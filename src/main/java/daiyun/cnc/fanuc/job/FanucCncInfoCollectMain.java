package daiyun.cnc.fanuc.job;

/**
 * @author godaiyun
 * @date 2018-07-19 10:46.
 */
public class FanucCncInfoCollectMain {

  public static void main(String[] args) {
    CncInfoCache cncInfoCache = new CncInfoCache();

    // 机台信息不定频率采集
    FanucCncInfoCollectThread collectThread = new FanucCncInfoCollectThread(cncInfoCache);
    collectThread.start();

    // 机台信息固定频率文件生成
    LocalDataMakerThread localDataMakerThread = new LocalDataMakerThread(cncInfoCache);
    localDataMakerThread.start();


  }
}
