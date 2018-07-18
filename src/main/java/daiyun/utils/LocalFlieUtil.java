package daiyun.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author daiyun
 * @date 2016-09-12 14:42.
 */
public class LocalFlieUtil {

  private static final Logger LOGGER = LogManager.getLogger(LocalFlieUtil.class.getName());

  private LocalFlieUtil() {
  }

  /**
   * 以追加的方式向文件中新写入一行数据.
   *
   * @param writeFilePath 写入的文件
   * @param content       写入的内容
   * @return 写入成功标志
   */
  public static synchronized boolean appendFile(String writeFilePath, String content) {
    try {
      FileWriter writer = new FileWriter(writeFilePath, true);
      writer.write(content);
      writer.write(System.getProperty("line.separator"));
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  public static synchronized boolean appendFile(String filePath, String fileName, String content) {
    createFile(filePath, fileName);
    try {
      FileWriter writer = new FileWriter(filePath + File.separator + fileName, true);
      writer.write(content);
      writer.write(System.getProperty("line.separator"));
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  /**
   * 多行文件写入.
   *
   * @param fileIndex
   * @param fileName
   * @param contentList
   */
  public static synchronized void saveFile(String fileIndex, String fileName, List<String> contentList) {
    createFile(fileIndex, fileName);
    String filePath = fileIndex + File.separator + fileName;
    File file = new File(filePath);
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
      for (String lineStr : contentList) {
        writer.append(lineStr);
        writer.newLine();
      }
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static synchronized void saveFile(String writeFilePath, Map<String, Integer> keyValue) {
    createFile(writeFilePath);
    File file = new File(writeFilePath);
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
      for (String key : keyValue.keySet()) {
        writer.append(key + " => " + keyValue.get(key));
        writer.newLine();
      }
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static synchronized void saveFile(String writeFilePath, String keyValue) {
    createFile(writeFilePath);
    File file = new File(writeFilePath);
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
      writer.append(keyValue);
      writer.newLine();
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 多行文件写入.
   *
   * @param fileIndex
   * @param fileName
   * @param contentList
   */
  public static synchronized void saveFile(String fileIndex, String fileName, Map<String, String> contentList) {
    createFile(fileIndex, fileName);
    String filePath = fileIndex + File.separator + fileName;
    File file = new File(filePath);
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
      for (String lineStr : contentList.keySet()) {
        writer.append(lineStr);
        writer.newLine();
      }
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 多行文件写入.
   *
   * @param fileIndex
   * @param fileName
   * @param contentList
   */
  static String fileName = "data";
  static String curentFlie = "data";
  static int fileNum = 1;

  public static synchronized void saveFile2(String fileIndex, List<String> contentList) {
    createFile(fileIndex, curentFlie);
    String filePath = fileIndex + File.separator + curentFlie;
    File file = new File(filePath);
    if (file.length() > 314572800L) {
      curentFlie = fileName + "_" + (fileNum + "");
      createFile(fileIndex, curentFlie);
      file = new File(fileIndex + File.separator + curentFlie);
      fileNum++;
    }
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
      for (String lineStr : contentList) {
        writer.append(lineStr);
        writer.newLine();
      }
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static synchronized void saveFile(String file, List<String> contentList) {
    LOGGER.info("写入文件：" + file + " 行数：" + contentList.size());
    createFile(file);
    File files = new File(file);
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(files, true));
      for (String lineStr : contentList) {
        writer.append(lineStr);
        writer.newLine();
      }
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static synchronized void saveFile(String file, Set<String> contentList) {
    createFile(file);
    File files = new File(file);
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(files, true));
      for (String lineStr : contentList) {
        writer.append(lineStr);
        writer.newLine();
      }
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 创建文件.
   *
   * @param fileIndex
   * @param fileName
   */
  public static void createFile(String fileIndex, String fileName) {
    try {
      File fileDir = new File(fileIndex);
      if (!fileDir.exists()) {
        fileDir.mkdirs();
      }
      String filePath = fileIndex + File.separator + fileName;
      File file = new File(filePath);
      if (!file.exists()) {
        try {
          file.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static void createFile(String file) {
    int pathEnd = file.lastIndexOf(File.separator);
    String path = file.substring(0, pathEnd + 1);
    String fileName = file.substring(pathEnd + 1);
    createFile(path, fileName);
  }

  /**
   * 安行阅读文件内容.
   *
   * @param filePath
   * @param encoding
   * @return
   */
  public static List<String> lineContent(String filePath, String encoding) {
    List<String> sb = new ArrayList<>();
    try {
      File file = new File(filePath);
      if (file.isFile() && file.exists()) {
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = bufferedReader.readLine();
        while (lineTxt != null) {
          sb.add(lineTxt);
          lineTxt = bufferedReader.readLine();
        }
        read.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sb;
  }

  /**
   * 读取文件全部内容(速度比readToString慢).
   *
   * @param filePath
   * @param encoding
   * @return
   */
  public static String allContent(String filePath, String encoding) {
    StringBuffer sb = new StringBuffer();
    try {
      File file = new File(filePath);
      if (file.isFile() && file.exists()) {
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = bufferedReader.readLine();
        String lineSeparator = System.getProperty("line.separator");
        while (lineTxt != null) {
          sb.append(lineTxt);
          sb.append(lineSeparator);
          lineTxt = bufferedReader.readLine();
        }
        read.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sb.toString();
  }

  /**
   * 读取文件全部内容（文件太大会出现内存溢出）.
   *
   * @param filePath
   * @param encoding
   * @return
   */
  public static String readToString(String filePath, String encoding) {
    File file = new File(filePath);
    Long filelength = file.length();
    byte[] filecontent = new byte[filelength.intValue()];
    try {
      FileInputStream in = new FileInputStream(file);
      in.read(filecontent);
      in.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      return new String(filecontent, encoding);
    } catch (UnsupportedEncodingException e) {
      System.err.println("The OS does not support " + encoding);
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 读取需要处理的文件路径.
   *
   * @param filepath
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
  public static synchronized List<String> pathtTaverse(String filepath, List<String> fileList) throws IOException {
    try {
      File file = new File(filepath);
      if (!file.isDirectory()) {
        String handleFilePath = file.getAbsolutePath();
        fileList.add(handleFilePath);
      } else if (file.isDirectory()) {
        String[] filelist = file.list();
        for (int i = 0; i < filelist.length; i++) {
          File readfile = new File(filepath + File.separator + filelist[i]);
          if (!readfile.isDirectory()) {
            String handleFilePath = readfile.getAbsolutePath();
            fileList.add(handleFilePath);
          } else if (readfile.isDirectory()) {
            pathtTaverse(filepath + File.separator + filelist[i], fileList);
          }
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
    return fileList;
  }


  /**
   * 安行读取文件内容.
   *
   * @param filePath
   */
  public static void getAllFileLine(String filePath, List<String> fileLineList, String encoding) {
    try {
      File file = new File(filePath);
      if (file.isFile() && file.exists()) {
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = bufferedReader.readLine();
        while (lineTxt != null) {
          fileLineList.add(lineTxt);
          lineTxt = bufferedReader.readLine();
        }
        read.close();
        bufferedReader.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
