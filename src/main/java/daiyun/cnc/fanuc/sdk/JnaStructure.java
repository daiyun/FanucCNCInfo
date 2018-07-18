package daiyun.cnc.fanuc.sdk;

import com.sun.jna.Structure;

import java.util.List;

/**
 * @author daiyun
 * @date 2018-07-04 14:45.
 */
public class JnaStructure extends Structure {

  @Override
  protected List getFieldOrder() {
    return GetClassAttr.allAttribute(this.getClass());
  }
}
