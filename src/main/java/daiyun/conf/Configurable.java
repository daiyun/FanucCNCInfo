package daiyun.conf;

public interface Configurable {

  /**
   * ser the configuration to be used by this object
   *
   * @param configuration
   */
  public void setConf(Configuration configuration);

  /**
   * return the configuration used by this object
   *
   * @return
   */
  Configuration getConf();
}
