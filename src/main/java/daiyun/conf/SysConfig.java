package daiyun.conf;

import java.io.File;

/**
 * @author daiyun
 * @date 2018-06-14 15:44.
 */
public class SysConfig {

  public static Configuration conf = new Configuration();

  static {
    conf.addConfiguration("conf" + File.separator + "conf.properties");
  }

}
