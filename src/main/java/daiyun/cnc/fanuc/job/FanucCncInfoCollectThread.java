package daiyun.cnc.fanuc.job;

import daiyun.cnc.fanuc.sdk.Fanuc;
import daiyun.cnc.fanuc.sdk.FanucCncAPI;
import daiyun.conf.SysConfig;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author daiyun
 * @date 2018-07-05 17:27.
 */
public class FanucCncInfoCollectThread extends Thread {

  private CncInfoCache cncInfoCache;
  private FanucCncAPI fanucCncAPI;

  public FanucCncInfoCollectThread(CncInfoCache cncInfoCache) {
    this.cncInfoCache = cncInfoCache;
  }

  @Override
  public void run() {
    fanucCncAPI = new FanucCncAPI(SysConfig.conf.getString("addr"),
        SysConfig.conf.getInteger("port").shortValue(),
        SysConfig.conf.getLong("timeout"));

    // 连接机台
    if (fanucCncAPI.connectCNC()) {

      try {
        while (true) {
          long s = System.currentTimeMillis();
          JSONObject jsonFile = new JSONObject();

          jsonFile.put("status", status());

          // 主轴位置信息
          jsonFile.put("position", position());

          // 阻力
//          jsonFile.put("resistance", resistance());

          // 刀号
          jsonFile.put("tool", toolNum());

          // 转速
          jsonFile.put("S", rpm());

          // 进给
          jsonFile.put("F", getF());

          cncInfoCache.setCurrentCncInfo(jsonFile);

          // 计算休眠时间

          // 信息写入文件
          if (!SysConfig.conf.getBoolean("debug")) {
            long e = System.currentTimeMillis();

            long sleepMillis = SysConfig.conf.getInteger("collectInterval") - (e - s);

            if (sleepMillis > 0) {
              Thread.sleep(sleepMillis);
            }
          } else {
            System.out.println(jsonFile);
            Thread.sleep(2000);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        fanucCncAPI.cancle();
      }
    }
  }

  /**
   * 获取机台状态信息
   *
   * @return
   */
  private int status() {
    int status = -1;
    Fanuc.ODBST odbst = fanucCncAPI.getCncStatus();
    switch (odbst.run) {
      case 1:
        // "调试或装料中！";//机台已经关机或联机中断!
        break;
      case 2:
        //"暂停！";
        break;
      case 3:
        // 加工中
        status = 3;
        break;
      default:
        break;
    }
    return status;
  }

  public JSONObject position() {
    JSONObject json = new JSONObject();
    int positiontype = -1;
    Fanuc.ODBPOS odbpos = fanucCncAPI.getPosition(positiontype);
    json.put("time", System.currentTimeMillis());
    Set<String> axis = new HashSet<>();
    axis.add("X");
    axis.add("Y");
    axis.add("Z");
    JSONArray jsonArray = asisInfo(odbpos, positiontype, axis);
    json.put("info", jsonArray);
    return json;
  }

  private JSONObject rpm() {
    JSONObject json = new JSONObject();
    Fanuc.ODBACT odbacts = fanucCncAPI.getS();
    json.put("time", System.currentTimeMillis());
    json.put("info", odbacts.data);
    return json;
  }

  private JSONObject getF() {
    JSONObject json = new JSONObject();
    Fanuc.ODBACT odbacts = fanucCncAPI.getF();
    json.put("time", System.currentTimeMillis());
    json.put("info", odbacts.data);
    return json;
  }

  private JSONObject toolNum() {
    JSONObject json = new JSONObject();
    json.put("info", fanucCncAPI.getToolInfo());
    json.put("time", System.currentTimeMillis());
    return json;
  }

  private JSONObject toolCount() {
    JSONObject json = new JSONObject();
    json.put("info", fanucCncAPI.getToolMachinedPart());
    json.put("time", System.currentTimeMillis());
    return json;
  }

  private JSONObject resistance() {
    JSONObject json = new JSONObject();
    json.put("info", fanucCncAPI.getResistance());
    json.put("time", System.currentTimeMillis());
    return json;
  }

  /**
   * 获取数据单位
   *
   * @param type
   * @return
   */
  public String getUnitType(Short type) {
    String unit = "";
    switch (type) {
      case 0:
        unit = "mm";
        break;
      case 1:
        unit = "inch";
        break;
      case 2:
        unit = "degree";
        break;
      default:
        break;
    }
    return unit;
  }

  /**
   * 0	:	absolute position
   * 1	:	machine position
   * 2	:	relative position
   * 3	:	distance to go
   * -1	:	all type
   * <p>
   * long    data;    position data
   * short   dec;     place of decimal point of positiondata
   * short   unit;    unit of position data
   * short   disp;    status of display
   * char    name;    axis name
   * char    suff;    subscript of axis name
   *
   * @param odbpos
   * @param type
   * @param axis
   * @return
   */
  public JSONArray asisInfo(Fanuc.ODBPOS odbpos, int type, Set<String> axis) {
    JSONArray resJson = new JSONArray();
    Class userCla = odbpos.getClass();

    for (int i = 1; i <= Fanuc.MAX_AXIS; i++) {
      try {
        Field f = userCla.getField("p" + i);
        f.setAccessible(true);
        Fanuc.POSELMALL p = (Fanuc.POSELMALL) f.get(odbpos);
        if (p != null) {
          String axisName = ((char) p.abs.name) + "";
          if (!axis.contains(axisName)) {
            continue;
          }
          JSONObject json = new JSONObject();
          json.put("name", axisName);
          switch (type) {
            case -1: {
              json.put("abs", formatAxisInfo(p.abs));
              json.put("mach", formatAxisInfo(p.mach));
              json.put("rel", formatAxisInfo(p.rel));
              json.put("dist", formatAxisInfo(p.dist));
              break;
            }
            case 0: {
              json.put("abs", formatAxisInfo(p.abs));
              break;
            }
            case 1: {
              json.put("mach", formatAxisInfo(p.mach));
              break;
            }
            case 2: {
              json.put("rel", formatAxisInfo(p.rel));
              break;
            }
            case 3: {
              json.put("dist", formatAxisInfo(p.dist));
              break;
            }
            default: {

            }
          }

          resJson.put(json);
        }
      } catch (NoSuchFieldException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return resJson;
  }

  /**
   * 处理主轴位置参数
   *
   * @param info
   * @return
   */
  public JSONObject formatAxisInfo(Fanuc.POSELM info) {
    JSONObject json = new JSONObject();
    short point = info.dec;
    int data = info.data.intValue();
    double position = 0.0f;
    position = data * Math.pow(10, -point);
    json.put("data", position);
    json.put("unit", getUnitType(info.unit));
    return json;
  }

}
