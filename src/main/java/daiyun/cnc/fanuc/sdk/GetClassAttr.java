package daiyun.cnc.fanuc.sdk;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author daiyun
 * @date 2018-07-04 14:39.
 */
public class GetClassAttr {
  public static List allAttribute(Class<?> c) {
    List<String> list = new ArrayList<>();
    Field[] fields = c.getDeclaredFields();
    for (Field field : fields) {
      String name = field.getName();
      if (name.length() > 0) {
        list.add(name);
      }
    }
    return list;
  }
  
}
