package daiyun.conf;

import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Configuration {

  private static final Logger logger = LogManager.getLogger(Configuration.class);

  private final Properties props = new Properties();

  public Configuration() {
  }

  /**
   * 通过文件添加配置文件.
   *
   * @param filename
   * @throws IOException
   */
  public void addConfiguration(String filename) {
    try {
      props.load(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
    } catch (IOException e) {
      logger.info("添加配置文件失败");
    }
  }

  /**
   * 添加resources 下配置文件.
   *
   * @param filename
   */
  public void addRootResources(String filename) {
    try {
      props.load(getClass().getClassLoader().getResourceAsStream(filename));
    } catch (IOException e) {
      logger.error("can't load resources:" + filename);
    }
  }

  /**
   * 通过classpath resource添加配置文件，同一包内的resource文件按相对路径
   * ，不同包的按绝对路径.
   * addResources("/com/rainmaker/haha.properties")
   * addResources("haha.properties")
   *
   * @param resourcesPath
   */
  public void addResources(String resourcesPath) {
    try {
      props.load(getClass().getResourceAsStream(resourcesPath));
    } catch (IOException e) {
      logger.error("can't load resources:" + resourcesPath);
    }
  }


  /**
   * 清空配置.
   */
  public void clear() {
    props.clear();
  }

  public Boolean getBoolean(String key, Boolean defaultValue) {
    String value = getTrimed(key);
    if (!Strings.isNullOrEmpty(value)) {
      return Boolean.parseBoolean(value.trim());
    }
    return defaultValue;
  }

  public Boolean getBoolean(String key) {
    return getBoolean(key, null);
  }

  public Integer getInteger(String key, Integer defaultValue) {
    String value = getTrimed(key);
    if (!Strings.isNullOrEmpty(value)) {
      return Integer.parseInt(value.trim());
    }
    return defaultValue;
  }

  public Integer getInteger(String key) {
    return getInteger(key, null);
  }

  public Long getLong(String key, Long defaultValue) {
    String value = getTrimed(key);
    if (!Strings.isNullOrEmpty(value)) {
      return Long.parseLong(value.trim());
    }
    return defaultValue;
  }

  public Long getLong(String key) {
    return getLong(key, null);
  }

  public Float getFloat(String key, Float defaultValue) {
    String value = getTrimed(key);
    if (!Strings.isNullOrEmpty(value)) {
      return Float.parseFloat(value.trim());
    }
    return defaultValue;
  }

  public Float getFloat(String key) {
    return getFloat(key, null);
  }

  public String getString(String key, String defaultValue) {
    return get(key, defaultValue);
  }

  public String getString(String key) {
    return get(key);
  }

  private String get(String key, String defaultValue) {
    String value = getTrimed(key);
    if (!Strings.isNullOrEmpty(value)) {
      return value;
    }
    return defaultValue;
  }

  private String get(String key) {
    return get(key, null);
  }

  public Class<?> getClass(String key, Class<?> defaultValue) {
    String value = getTrimed(key);
    if (!Strings.isNullOrEmpty(value)) {
      try {
        Class<?> clazz = Class.forName(value);
        return clazz;
      } catch (ClassNotFoundException e) {
        return defaultValue;
      }
    }
    return defaultValue;
  }

  public Class<?> getClass(String key) {
    return getClass(key, null);
  }

  public Properties getProps() {
    return props;
  }

  private String getTrimed(String key) {
    String val = props.getProperty(key);
    return val != null ? val.trim() : null;
  }

  @Override
  public String toString() {
    return "Configuration{" +
        "props=" + props +
        '}';
  }
}
