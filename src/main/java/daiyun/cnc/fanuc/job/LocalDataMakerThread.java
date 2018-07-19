package daiyun.cnc.fanuc.job;

import daiyun.conf.SysConfig;
import daiyun.utils.LocalFlieUtil;
import daiyun.utils.TimeFormat;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author godaiyun
 * @date 2018-07-19 09:47.
 */
public class LocalDataMakerThread extends Thread {
  private CncInfoCache cncInfoCache;

  public LocalDataMakerThread(CncInfoCache cncInfoCache) {
    this.cncInfoCache = cncInfoCache;
  }


  @Override
  public void run() {
    while (true) {
      try {

        List<JSONObject> minuteData = cncInfoCache.getCurrentMinuteCncInfoWrite();

        if (minuteData != null) {
          long s = System.currentTimeMillis();

          List<String> str = makeFileContetn(minuteData);

          Date currentTime = new Date();
          String collectTime = TimeFormat.dateFormat("yyyy-MM-dd-HH-mm", currentTime);
          LocalFlieUtil.saveFile(SysConfig.conf.getString("dataDir") + File.separator + TimeFormat.dateFormat("yyyy-MM-dd", currentTime) + File.separator,
              SysConfig.conf.getString("dataFilePre") + collectTime + ".txt",
              str);

          long e = System.currentTimeMillis();
          long sleepMillis = SysConfig.conf.getInteger("collectInterval") - (e - s);

          System.out.println(sleepMillis);

          if (500 - sleepMillis > 0) {
            Thread.sleep(500 - sleepMillis);
          }
        } else {
          Thread.sleep(500);
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public List<String> makeFileContetn(List<JSONObject> minuteData) {

    List<String> listRes = new ArrayList<>();
    StringBuffer sb = new StringBuffer();

    long current = 0;
    JSONObject preJdata = minuteData.get(0);

    int sbIndex = 0;
    for (int i = 1; i < minuteData.size(); i++) {
      JSONObject currentJdata = minuteData.get(i);
      long time = currentJdata.getLong("updateTime");
      long millis = (time % 60000) * 10;

      for (; current < millis; current++) {
        if (sbIndex > 10000) {
          listRes.add(sb.toString());
          sb = new StringBuffer();
          sbIndex = 0;
        }
        sbIndex++;
        sb.append(preJdata.toString() + "\r\n");
      }
      preJdata = currentJdata;
    }

    for (; current < 600000; current++) {
      if (sbIndex > 10000) {
        listRes.add(sb.toString());
        sb = new StringBuffer();
        sbIndex = 0;
      }
      sbIndex++;
      sb.append(preJdata.toString() + "\r\n");
    }

    listRes.add(sb.toString());

    return listRes;
  }


}
