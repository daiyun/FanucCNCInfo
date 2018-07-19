package daiyun.cnc.fanuc.job;

import daiyun.conf.SysConfig;
import daiyun.utils.TimeFormat;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author godaiyun
 * @date 2018-07-19 09:49.
 */
public class CncInfoCache {

  private final String lock = "";

  private JSONObject currentCncInfo;
  private JSONObject preCncInfo;

  private List<JSONObject> currentMinuteCncInfo = new ArrayList<>(60 * (1000 / SysConfig.conf.getInteger("collectInterval")));
  private List<JSONObject> currentMinuteCncInfoWrite = new ArrayList<>(currentMinuteCncInfo.size() + 1);
  private String currentMinute = TimeFormat.currentTimeFormat("yyyy-MM-dd-HH-mm");

  public JSONObject getCurrentCncInfo() {
    return currentCncInfo;
  }

  public void setCurrentCncInfo(JSONObject currentCncInfo) {

    Date date = new Date();
    currentCncInfo.put("updateTime", date.getTime());

    this.currentCncInfo = currentCncInfo;

    String minute = TimeFormat.dateFormat("yyyy-MM-dd-HH-mm", date);

    if (minute.equals(currentMinute)) {
      currentMinuteCncInfo.add(currentCncInfo);
    } else {

      synchronized (lock) {
        if (preCncInfo != null) {
          currentMinuteCncInfoWrite.add(preCncInfo);
        }
        currentMinuteCncInfoWrite.addAll(currentMinuteCncInfo);
        currentMinuteCncInfo.clear();
      }

      currentMinuteCncInfo.add(currentCncInfo);
      currentMinute = minute;
    }
  }

  public List<JSONObject> getCurrentMinuteCncInfoWrite() {
    List<JSONObject> writeList = new ArrayList<>();
    synchronized (lock) {
      if (currentMinuteCncInfoWrite.size() > 0) {
        writeList.addAll(currentMinuteCncInfoWrite);
        preCncInfo = currentMinuteCncInfoWrite.get(currentMinuteCncInfo.size() - 1);
        currentMinuteCncInfoWrite.clear();
        return writeList;
      } else {
        return null;
      }
    }
  }


  public List<JSONObject> getCurrentMinuteCncInfo() {
    return currentMinuteCncInfo;
  }

  public void setCurrentMinuteCncInfo(List<JSONObject> currentMinuteCncInfo) {
    this.currentMinuteCncInfo = currentMinuteCncInfo;
  }

  public String getCurrentMinute() {
    return currentMinute;
  }

  public void setCurrentMinute(String currentMinute) {
    this.currentMinute = currentMinute;
  }

}
