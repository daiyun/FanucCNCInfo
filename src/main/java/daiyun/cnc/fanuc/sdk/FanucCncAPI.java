package daiyun.cnc.fanuc.sdk;

import org.json.JSONObject;

/**
 * @author daiyun
 * @date 2018-06-28 10:45.
 */
public class FanucCncAPI {

  protected String ip;
  protected short port;
  protected long timeout;

  protected Fanuc fanuc = Fanuc.FANUC;
  protected final int MAX_ASIS = 8;
  protected int[] flibHndl = new int[MAX_ASIS];

  public FanucCncAPI(String ip, short port, long timeout) {
    this.ip = ip;
    this.port = port;
    this.timeout = timeout;
  }

  /**
   * connect
   *
   * @return
   */
  public boolean connectCNC() {
    short res = fanuc.cnc_allclibhndl3(ip, port, timeout, flibHndl);
    if (res != Fanuc.EW_OK) {
      return false;
    }
    return true;
  }

  /**
   * free handle
   */
  public void cancle() {
    fanuc.cnc_freelibhndl(flibHndl[0]);
  }

  /**
   * position
   *
   * @param type
   * @return
   */
  public Fanuc.ODBPOS getPosition(Integer type) {
    int[] maxAsis = new int[MAX_ASIS];
    maxAsis[0] = MAX_ASIS;
    Fanuc.ODBPOS odbpos = new Fanuc.ODBPOS();
    short res = fanuc.cnc_rdposition(flibHndl[0], type.shortValue(), maxAsis, odbpos);
    if (res != Fanuc.EW_OK) {
      return null;
    }
    return odbpos;
  }

  /**
   * 获取机台进给
   *
   * @return
   */
  public Fanuc.ODBACT getF() {
    Fanuc.ODBACT odbact = new Fanuc.ODBACT();
    short res = fanuc.cnc_actf(flibHndl[0], odbact);
    if (res != Fanuc.EW_OK) {
      return null;
    }
    return odbact;
  }

  /**
   * 获取机台转速
   *
   * @return
   */
  public Fanuc.ODBACT getS() {
    Fanuc.ODBACT spindle = new Fanuc.ODBACT();
    short res = fanuc.cnc_acts(flibHndl[0], spindle);
    if (res != Fanuc.EW_OK) {
      return null;
    }
    return spindle;
  }

  /**
   * 获取机台指定轴转速
   *
   * @return
   */
  public Fanuc.ODBACT2[] get2S(Integer type) {
    Fanuc.ODBACT2 spindle[] = new Fanuc.ODBACT2[8];
    short res = fanuc.cnc_acts2(flibHndl[0], type.shortValue(), spindle);
    if (res != Fanuc.EW_OK) {
      return null;
    }
    return spindle;
  }


  /**
   * 获取工具使用次数
   * @return
   */
  public int getToolMachinedPart() {
    int count = -1;
    Fanuc.ODBUSEGRP odbusegrp = new Fanuc.ODBUSEGRP();
    short ret = fanuc.cnc_rdtlusegrp(flibHndl[0], odbusegrp);
    if (ret != Fanuc.EW_OK || odbusegrp.use == 0) {
      return -1;
    }

    Fanuc.ODBTG toolGroupInfo = new Fanuc.ODBTG();
    ret = fanuc.cnc_rdtoolgrp(flibHndl[0], (short) odbusegrp.use, (short) (20 + 20 * 1), toolGroupInfo);
    if (ret == Fanuc.EW_OK) {
      System.out.println("正在运行的刀片组号;" + odbusegrp.use + "   寿命：" + toolGroupInfo.life + "  次数：" + toolGroupInfo.count);
      count = toolGroupInfo.count;
    }

    return count;
  }

  /**
   * 获取工具使用信息，含寿命、使用次数、当前工具编号等信息
   * @return
   */
  public JSONObject getToolInfo() {

    Fanuc.ODBUSEGRP toolGroup = new Fanuc.ODBUSEGRP();
    short ret = fanuc.cnc_rdtlusegrp(flibHndl[0], toolGroup);
    if (ret != Fanuc.EW_OK || toolGroup.use == 0) {
      return null;
    }

    Fanuc.ODBTG toolGroupInfo = new Fanuc.ODBTG();
    ret = fanuc.cnc_rdtoolgrp(flibHndl[0], (short) toolGroup.use, (short) (20 + 20 * 4), toolGroupInfo);
    if (ret != Fanuc.EW_OK) {
      return null;
    }

    JSONObject json = new JSONObject();
    json.put("currentTool",toolGroupInfo.data.data1.tool_num);
    json.put("life",toolGroupInfo.life);
    json.put("count",toolGroupInfo.count);
    return json;
  }

  /**
   * 函数作用待探明
   * @return
   */
  public double getResistance() {
    return 0.0;
  }


  /**
   * 获取机台刀号
   * TODO: 全是信息打印，作用需要明确
   */
  public void getTools() {
    Fanuc.ODBTLIFE5 tool = new Fanuc.ODBTLIFE5();//读取刀片组的序号
    Fanuc.ODBTLIFE2 tool1 = new Fanuc.ODBTLIFE2();//读取刀片组的全部数量
    Fanuc.ODBTLIFE3 tool2 = new Fanuc.ODBTLIFE3();//刀具的数量
    Fanuc.ODBTLIFE4 life = new Fanuc.ODBTLIFE4();
    Fanuc.ODBTG btg = new Fanuc.ODBTG();
    Fanuc.ODBUSEGRP grp = new Fanuc.ODBUSEGRP();
    Fanuc.IODBTGI btgi = new Fanuc.IODBTGI();

    short start = 0;//开始的刀具组
    short end = 22;//结束的刀具组

    short length = (short) (8 + 16 * (end - start));
    short ret = fanuc.cnc_rdgrpinfo(flibHndl[0], start, end, length, btgi);
    System.out.println("cnc_rdgrpinfo:" + ret);

    if (ret == 0) {
      System.out.println(btgi.data.data1.count_value);
    }

    int m = 2;
    ret = fanuc.cnc_rdgrpid2(flibHndl[0], m, tool);
    System.out.println("cnc_rdgrpid2:" + ret);

    ret = fanuc.cnc_rdngrp(flibHndl[0], tool1);//刀片组的全部数量
    System.out.println("all:" + ret);

    short group_numer = (short) (tool.data);

    ret = fanuc.cnc_rdntool(flibHndl[0], group_numer, tool2);//刀具的数量
    System.out.println("cnc_rdntool:" + ret);

    ret = fanuc.cnc_rdlife(flibHndl[0], group_numer, tool2);//刀具寿命
    System.out.println("cnc_rdlife:" + ret);

    ret = fanuc.cnc_rdcount(flibHndl[0], group_numer, tool2);//刀具计时器
    System.out.println("cnc_rdcount:" + ret);

    ret = fanuc.cnc_rdtoolgrp(flibHndl[0], (short) 2, (short) (20 + 20 * 1), btg);//根据刀组号读出所有信息，很重要；
    System.out.println("cnc_rdtoolgrp:" + ret);

    ret = fanuc.cnc_rdtlusegrp(flibHndl[0], grp);//读出正在使用的到组号；
    System.out.println("cnc_rdtlusegrp:" + ret);

    System.out.println("刀片组号：" + group_numer);
    System.out.println("type:" + tool.type);
    System.out.println("刀片组的全部数量" + btgi.data.data1.count_value);
    System.out.println("刀片号：" + tool2.data);
    System.out.println("刀片组号;" + group_numer + "   寿命：" + tool2.data);
    System.out.println("刀片组号;" + group_numer + "   寿命计时：" + tool2.data);

/*
    Fanuc.ODBUSEGRP toolGroup = new Fanuc.ODBUSEGRP();
    Fanuc.ODBTG toolGroupInfo = new Fanuc.ODBTG();

    short ret = Fanuc.cnc_rdtlusegrp(flibHndl[0], toolGroup);

    System.out.println("===" + ret);
    System.out.println(toolGroup.use);

    Integer usingToolGroupNumber = toolGroup.use;
    System.out.println(usingToolGroupNumber);

    short length = 20 + 20 * 4;

    ret = Fanuc.cnc_rdtoolgrp(flibHndl[0], usingToolGroupNumber.shortValue(), length, toolGroupInfo);

    System.out.println("ret:" + ret);
    System.out.println(toolGroupInfo.data.data1.tool_num);
    System.out.println(toolGroupInfo);*/
  }

  /**
   * 获取机台负载
   *
   * @param type
   * @return
   */
  public Fanuc.ODBSPN[] getLoad(Integer type) {
    Fanuc.ODBSPN[] odbspn = new Fanuc.ODBSPN[8];
    short res = fanuc.cnc_rdspload(flibHndl[0], type.shortValue(), odbspn);
    if (res != Fanuc.EW_OK) {
      System.out.println("err:" + res);
      return null;
    }
    return odbspn;
  }


  /**
   * cnc status infomation
   * 获取CNC 状态信息
   *
   * @return
   */
  public Fanuc.ODBST getCncStatus() {
    Fanuc.ODBST odbst = new Fanuc.ODBST();
    short res = fanuc.cnc_statinfo(flibHndl[0], odbst);
    if (res != Fanuc.EW_OK) {
      System.out.println("err:" + res);
      return null;
    }
    return odbst;
  }

  /**
   * 获取加工工件数量
   * TODO: 这个函数好像没有写完
   */
  public void getProcessTimes() {
    Fanuc.ODBPARANUM odbparanum = new Fanuc.ODBPARANUM();

    fanuc.cnc_rdparanum(flibHndl[0], odbparanum);

    Fanuc.IODBPSD iodbpsd2s = new Fanuc.IODBPSD();

    short[] s_number = new short[8];
    s_number[0] = 6712;

    short[] e_number = new short[8];
    e_number[0] = 6712;

    short[] length = new short[8];
    length[0] = 16;

    short res = fanuc.cnc_rdparar(flibHndl[0], s_number, (short) -1, e_number, length, iodbpsd2s);

    System.out.println();
  }

}
