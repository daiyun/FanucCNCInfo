package daiyun.cnc.fanuc.sdk;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.ptr.IntByReference;

/**
 * @author daiyun
 * @date 2018-06-28 10:40.
 */
public interface Fanuc extends Library {
  Fanuc FANUC = (Fanuc) Native.loadLibrary("lib/64/Fwlib64", Fanuc.class);
  /* Axis define */
//#if FS30D
//  public static final short MAX_AXIS = 32;
  //#elif M_AXIS2
//  public static final short MAX_AXIS = 24;
  //#elif FS15D
//  public static final short MAX_AXIS = 10;
  //#else
  public static final short MAX_AXIS = 8;
  //#endif
  public static final short ALL_AXES = (-1);
  public static final short ALL_SPINDLES = (-1);
  public static final short EW_OK = (short) focas_ret.EW_OK.getValue();

  public enum focas_ret {
    EW_PROTOCOL(-17), // protocol error
    EW_SOCKET(-16), // Windows socket error
    EW_NODLL(-1), // DLL not exist error
    EW_BUS(-11), // bus error
    EW_SYSTEM2(-10), // system error
    EW_HSSB(-9), // hssb communication error
    EW_HANDLE(-8), // Windows library handle error
    EW_VERSION(-7), // CNC/PMC version missmatch
    EW_UNEXP(-6), // abnormal error
    EW_SYSTEM(-5), // system error
    EW_PARITY(-4), // shared RAM parity error
    EW_MMCSYS(-3), // emm386 or mmcsys install error
    EW_RESET(-2), // reset or stop occured error
    EW_BUSY(-1), // busy error
    EW_OK(0), // no problem
    EW_FUNC(1), // command prepare error
    EW_NOPMC(1), // pmc not exist
    EW_LENGTH(2), // data block length error
    EW_NUMBER(3), // data number error
    EW_RANGE(3), // address range error
    EW_ATTRIB(4), // data attribute error
    EW_TYPE(4), // data type error
    EW_DATA(5), // data error
    EW_NOOPT(6), // no option error
    EW_PROT(7), // write protect error
    EW_OVRFLOW(8), // memory overflow error
    EW_PARAM(9), // cnc parameter not correct error
    EW_BUFFER(10), // buffer error
    EW_PATH(11), // path error
    EW_MODE(12), // cnc mode error
    EW_REJECT(13), // execution rejected error
    EW_DTSRVR(14), // data server error
    EW_ALARM(15), // alarm has been occurred
    EW_STOP(16), // CNC is not running
    EW_PASSWD(17), // protection data error
    /*
        Result codes of DNC operation
    */
    DNC_NORMAL(-1), // normal completed
    DNC_CANCEL(-32768), // DNC operation was canceled by CNC
    DNC_OPENERR(-514), // file open error
    DNC_NOFILE(-516), // file not found
    DNC_READERR(-517); // read error
    public static final int SIZE = java.lang.Integer.SIZE;
    private int intValue;
    private static java.util.HashMap<Integer, focas_ret> mappings;

    private static java.util.HashMap<Integer, focas_ret> getMappings() {
      if (mappings == null) {
        synchronized (focas_ret.class) {
          if (mappings == null) {
            mappings = new java.util.HashMap<Integer, focas_ret>();
          }
        }
      }
      return mappings;
    }

    private focas_ret(int value) {
      intValue = value;
      getMappings().put(value, this);
    }

    public int getValue() {
      return intValue;
    }

    public static focas_ret forValue(int value) {
      return getMappings().get(value);
    }
  }

  /*--------------------*/
  /*                    */
  /* Structure Template */
  /*                    */
  /*--------------------*/
  /*-------------------------------------*/
  /* CNC: Control axis / spindle related */
  /*-------------------------------------*/
  /* cnc_actf:read actual axis feedrate(F) */
  /* cnc_acts:read actual spindle speed(S) */
  public static class ODBACT extends JnaStructure {
    public short[] dummy = new short[2]; // dummy
    public NativeLong data; // actual feed / actual spindle
  }

  /* cnc_acts2:read actual spindle speed(S) */
  /* (All or specified ) */
  public static class ODBACT2 extends JnaStructure {
    public short datano; // spindle number
    public short type; // dummy
    public NativeLong[] data = new NativeLong[MAX_AXIS]; // spindle data
  }

  /* cnc_absolute:read absolute axis position */
  /* cnc_machine:read machine axis position */
  /* cnc_relative:read relative axis position */
  /* cnc_distance:read distance to go */
  /* cnc_skip:read skip position */
  /* cnc_srvdelay:read servo delay value */
  /* cnc_accdecdly:read acceleration/deceleration delay value */
  /* cnc_absolute2:read absolute axis position 2 */
  /* cnc_relative2:read relative axis position 2 */
  public static class ODBAXIS extends JnaStructure {
    public short dummy; // dummy
    public short type; // axis number
    public int[] data = new int[MAX_AXIS]; // data value
  }

  /* cnc_rddynamic:read all dynamic data */
  public static class FAXIS extends JnaStructure {
    public int[] absolute = new int[MAX_AXIS]; // absolute position
    public int[] machine = new int[MAX_AXIS]; // machine position
    public int[] relative = new int[MAX_AXIS]; // relative position
    public int[] distance = new int[MAX_AXIS]; // distance to go
  }

  public static class OAXIS extends JnaStructure {
    public int absolute; // absolute position
    public int machine; // machine position
    public int relative; // relative position
    public int distance; // distance to go
  }

  public static class ODBDY_1 extends JnaStructure {
    public short dummy;
    public short axis; // axis number
    public short alarm; // alarm status
    public short prgnum; // current program number
    public short prgmnum; // main program number
    public int seqnum; // current sequence number
    public int actf; // actual feedrate
    public int acts; // actual spindle speed
    public FAXIS pos = new FAXIS();
  }

  public static class ODBDY_2 extends JnaStructure {
    public short dummy;
    public short axis; // axis number
    public short alarm; // alarm status
    public short prgnum; // current program number
    public short prgmnum; // main program number
    public int seqnum; // current sequence number
    public int actf; // actual feedrate
    public int acts; // actual spindle speed
    public OAXIS pos = new OAXIS();
  }

  /* cnc_rddynamic2:read all dynamic data */
  public static class ODBDY2_1 extends JnaStructure {
    public short dummy;
    public short axis; // axis number
    public int alarm; // alarm status
    public int prgnum; // current program number
    public int prgmnum; // main program number
    public int seqnum; // current sequence number
    public int actf; // actual feedrate
    public int acts; // actual spindle speed
    public FAXIS pos = new FAXIS();
  }

  public static class ODBDY2_2 extends JnaStructure {
    public short dummy;
    public short axis; // axis number
    public int alarm; // alarm status
    public int prgnum; // current program number
    public int prgmnum; // main program number
    public int seqnum; // current sequence number
    public int actf; // actual feedrate
    public int acts; // actual spindle speed
    public OAXIS pos = new OAXIS(); // In case of 1 axis
  }

  /* cnc_wrrelpos:set origin / preset relative axis position */
  public static class IDBWRR extends JnaStructure {
    public short datano; // dummy
    public short type; // axis number
    public int[] data = new int[MAX_AXIS]; // preset data
  }

  /* cnc_prstwkcd:preset work coordinate */
  public static class IDBWRA extends JnaStructure {
    public short datano; // dummy
    public short type; // axis number
    public int[] data = new int[MAX_AXIS]; // preset data
  }

  /* cnc_rdmovrlap:read manual overlapped motion value */
  public static class IODBOVL extends JnaStructure {
    public short datano; // dummy
    public short type; // axis number
    public int[] datanew = new int[2 * MAX_AXIS]; // data value:[2][MAX_AXIS]
  }

  /* cnc_rdspload:read load information of serial spindle */
  /* cnc_rdspmaxrpm:read maximum r.p.m. ratio of serial spindle */
  /* cnc_rdspgear:read gear ratio of serial spindle */
  public static class ODBSPN extends JnaStructure {
    public short datano; // dummy
    public short type; // axis number
    public short[] data = new short[4]; // preset data
  }

  /* cnc_rdposition:read tool position */
  public static class POSELM extends JnaStructure {
    public NativeLong data; // position data
    public short dec; // place of decimal point of position data
    public short unit; // unit of position data
    public short disp; // status of display
    public byte name; // axis name
    public byte suff; // axis name preffix
  }

  public static class POSELMALL extends JnaStructure {
    public POSELM abs = new POSELM();
    public POSELM mach = new POSELM();
    public POSELM rel = new POSELM();
    public POSELM dist = new POSELM();
  }

  //#if M_AXIS2
  public static class ODBPOS extends JnaStructure {
    public POSELMALL p1 = new POSELMALL();
    public POSELMALL p2 = new POSELMALL();
    public POSELMALL p3 = new POSELMALL();
    public POSELMALL p4 = new POSELMALL();
    public POSELMALL p5 = new POSELMALL();
    public POSELMALL p6 = new POSELMALL();
    public POSELMALL p7 = new POSELMALL();
    public POSELMALL p8 = new POSELMALL();
    public POSELMALL p9 = new POSELMALL();
    public POSELMALL p10 = new POSELMALL();
    public POSELMALL p11 = new POSELMALL();
    public POSELMALL p12 = new POSELMALL();
    public POSELMALL p13 = new POSELMALL();
    public POSELMALL p14 = new POSELMALL();
    public POSELMALL p15 = new POSELMALL();
    public POSELMALL p16 = new POSELMALL();
    public POSELMALL p17 = new POSELMALL();
    public POSELMALL p18 = new POSELMALL();
    public POSELMALL p19 = new POSELMALL();
    public POSELMALL p20 = new POSELMALL();
    public POSELMALL p21 = new POSELMALL();
    public POSELMALL p22 = new POSELMALL();
    public POSELMALL p23 = new POSELMALL();
    public POSELMALL p24 = new POSELMALL();
    // In case of 24 axes.
    // if you need the more information, you must be add the member.
  }

  /* cnc_rdhndintrpt:read handle interruption */
  public static class ODBHND_data extends JnaStructure {
    public POSELM input = new POSELM(); // input unit
    public POSELM output = new POSELM(); // output unit
  }

  //#if M_AXIS2
  public static class ODBHND extends JnaStructure {
    public ODBHND_data p1 = new ODBHND_data();
    public ODBHND_data p2 = new ODBHND_data();
    public ODBHND_data p3 = new ODBHND_data();
    public ODBHND_data p4 = new ODBHND_data();
    public ODBHND_data p5 = new ODBHND_data();
    public ODBHND_data p6 = new ODBHND_data();
    public ODBHND_data p7 = new ODBHND_data();
    public ODBHND_data p8 = new ODBHND_data();
    public ODBHND_data p9 = new ODBHND_data();
    public ODBHND_data p10 = new ODBHND_data();
    public ODBHND_data p11 = new ODBHND_data();
    public ODBHND_data p12 = new ODBHND_data();
    public ODBHND_data p13 = new ODBHND_data();
    public ODBHND_data p14 = new ODBHND_data();
    public ODBHND_data p15 = new ODBHND_data();
    public ODBHND_data p16 = new ODBHND_data();
    public ODBHND_data p17 = new ODBHND_data();
    public ODBHND_data p18 = new ODBHND_data();
    public ODBHND_data p19 = new ODBHND_data();
    public ODBHND_data p20 = new ODBHND_data();
    public ODBHND_data p21 = new ODBHND_data();
    public ODBHND_data p22 = new ODBHND_data();
    public ODBHND_data p23 = new ODBHND_data();
    public ODBHND_data p24 = new ODBHND_data();
    // In case of 24 axes.
    // if you need the more information, you must be add the member.
  }

  /* cnc_rdspeed:read current speed */
  public static class SPEEDELM extends JnaStructure {
    public long data; // speed data
    public short dec; // decimal position
    public short unit; // data unit
    public short disp; // display flag
    public char name; // name of data
    public char suff; // suffix
  }

  public static class ODBSPEED extends JnaStructure {
    public SPEEDELM actf = new SPEEDELM(); // actual feed rate
    public SPEEDELM acts = new SPEEDELM(); // actual spindle speed
  }

  /* cnc_rdsvmeter:read servo load meter */
  /* cnc_rdspmeter:read spindle load meter */
  public static class LOADELM extends JnaStructure {
    public int data; // load meter
    public short dec; // decimal position
    public short unit; // unit

    public byte name; // name of data
    public byte suff1; // suffix
    public byte suff2; // suffix
    public byte reserve; // reserve
  }

  public static class ODBSVLOAD extends JnaStructure {
    public LOADELM svload1 = new LOADELM(); // servo load meter
    public LOADELM svload2 = new LOADELM(); // servo load meter
    public LOADELM svload3 = new LOADELM(); // servo load meter
    public LOADELM svload4 = new LOADELM(); // servo load meter
    public LOADELM svload5 = new LOADELM(); // servo load meter
    public LOADELM svload6 = new LOADELM(); // servo load meter
    public LOADELM svload7 = new LOADELM(); // servo load meter
    public LOADELM svload8 = new LOADELM(); // servo load meter
  }

  /* cnc_rdexecpt:read execution program pointer */
  public static class PRGPNT extends JnaStructure {
    public int prog_no; // program number
    public int blk_no; // block number
  }

  public static class ODBSPLOAD_data extends JnaStructure {
    public LOADELM spload = new LOADELM(); // spindle load meter
    public LOADELM spspeed = new LOADELM(); // spindle speed
  }

  public static class ODBSPLOAD extends JnaStructure {
    public ODBSPLOAD_data spload1 = new ODBSPLOAD_data(); // spindle load
    public ODBSPLOAD_data spload2 = new ODBSPLOAD_data(); // spindle load
    public ODBSPLOAD_data spload3 = new ODBSPLOAD_data(); // spindle load
    public ODBSPLOAD_data spload4 = new ODBSPLOAD_data(); // spindle load
  }

  /* cnc_rd5axmandt:read manual feed for 5-axis machining */
  public static class ODB5AXMAN extends JnaStructure {
    public short type1;
    public short type2;
    public short type3;
    public int data1;
    public int data2;
    public int data3;
    public int c1;
    public int c2;
    public int dummy;
    public int td;
    public int r1;
    public int r2;
    public int vr;
    public int h1;
    public int h2;
  }

  /*----------------------*/
  /* CNC: Program related */
  /*----------------------*/
  /* cnc_rddncdgndt:read the diagnosis data of DNC operation */
  public static class ODBDNCDGN extends JnaStructure {
    public short ctrl_word;
    public short can_word;
    public char[] nc_file = new char[16];
    public short read_ptr;
    public short write_ptr;
    public short empty_cnt;
    public int total_size;
  }

  /* cnc_upload:upload NC program */
  /* cnc_cupload:upload NC program(conditional) */
  public static class ODBUP extends JnaStructure {
    public short[] dummy = new short[2]; // dummy
    public char[] data = new char[256]; // data
  } // In case that the number of data is 256

  /* cnc_buff:read buffer status for downloading/verification NC program */
  public static class ODBBUF extends JnaStructure {
    public short[] dummy = new short[2]; // dummy
    public short data; // buffer status
  }

  /* cnc_rdprogdir:read program directory */
  public static class PRGDIR extends JnaStructure {
    public char[] prg_data = new char[256]; // directory data
  } // In case that the number of data is 256

  /* cnc_rdproginfo:read program information */
  public static class ODBNC_1 extends JnaStructure {
    public short reg_prg; // registered program number
    public short unreg_prg; // unregistered program number
    public int used_mem; // used memory area
    public int unused_mem; // unused memory area
  }

  public static class ODBNC_2 extends JnaStructure {
    public char[] asc = new char[31]; // ASCII string type
  }

  /* cnc_rdprgnum:read program number under execution */
  public static class ODBPRO extends JnaStructure {
    public short[] dummy = new short[2]; // dummy
    public short data; // running program number
    public short mdata; // main program number
  }

  /* cnc_exeprgname:read program name under execution */
  public static class ODBEXEPRG extends JnaStructure {
    public char[] name = new char[36]; // running program name
    public int o_num; // running program number
  }

  /* cnc_rdseqnum:read sequence number under execution */
  public static class ODBSEQ extends JnaStructure {
    public short[] dummy = new short[2]; // dummy
    public int data; // sequence number
  }

  /* cnc_rdmdipntr:read execution pointer for MDI operation */
  public static class ODBMDIP extends JnaStructure {
    public short mdiprog; // exec. program number
    public int mdipntr; // exec. pointer
    public short crntprog; // prepare program number
    public int crntpntr; // prepare pointer
  }

  /* cnc_rdaxisdata:read various axis data */
  public static class ODBAXDT_data extends JnaStructure {
    public String name = StringHelper.repeatChar(' ', 4); // axis name
    public int data; // position data
    public short dec; // decimal position
    public short unit; // data unit
    public short flag; // flags
    public short reserve; // reserve
  }

  public static class ODBAXDT extends JnaStructure {
    public ODBAXDT_data data1 = new ODBAXDT_data();
    public ODBAXDT_data data2 = new ODBAXDT_data();
    public ODBAXDT_data data3 = new ODBAXDT_data();
    public ODBAXDT_data data4 = new ODBAXDT_data();
    public ODBAXDT_data data5 = new ODBAXDT_data();
    public ODBAXDT_data data6 = new ODBAXDT_data();
    public ODBAXDT_data data7 = new ODBAXDT_data();
    public ODBAXDT_data data8 = new ODBAXDT_data();
    public ODBAXDT_data data9 = new ODBAXDT_data();
    public ODBAXDT_data data10 = new ODBAXDT_data();
    public ODBAXDT_data data11 = new ODBAXDT_data();
    public ODBAXDT_data data12 = new ODBAXDT_data();
    public ODBAXDT_data data13 = new ODBAXDT_data();
    public ODBAXDT_data data14 = new ODBAXDT_data();
    public ODBAXDT_data data15 = new ODBAXDT_data();
    public ODBAXDT_data data16 = new ODBAXDT_data();
    public ODBAXDT_data data17 = new ODBAXDT_data();
    public ODBAXDT_data data18 = new ODBAXDT_data();
    public ODBAXDT_data data19 = new ODBAXDT_data();
    public ODBAXDT_data data20 = new ODBAXDT_data();
    public ODBAXDT_data data21 = new ODBAXDT_data();
    public ODBAXDT_data data22 = new ODBAXDT_data();
    public ODBAXDT_data data23 = new ODBAXDT_data();
    public ODBAXDT_data data24 = new ODBAXDT_data();
    public ODBAXDT_data data25 = new ODBAXDT_data();
    public ODBAXDT_data data26 = new ODBAXDT_data();
    public ODBAXDT_data data27 = new ODBAXDT_data();
    public ODBAXDT_data data28 = new ODBAXDT_data();
    public ODBAXDT_data data29 = new ODBAXDT_data();
    public ODBAXDT_data data30 = new ODBAXDT_data();
    public ODBAXDT_data data31 = new ODBAXDT_data();
    public ODBAXDT_data data32 = new ODBAXDT_data();
    public ODBAXDT_data data33 = new ODBAXDT_data();
    public ODBAXDT_data data34 = new ODBAXDT_data();
    public ODBAXDT_data data35 = new ODBAXDT_data();
    public ODBAXDT_data data36 = new ODBAXDT_data();
    public ODBAXDT_data data37 = new ODBAXDT_data();
    public ODBAXDT_data data38 = new ODBAXDT_data();
    public ODBAXDT_data data39 = new ODBAXDT_data();
    public ODBAXDT_data data40 = new ODBAXDT_data();
    public ODBAXDT_data data41 = new ODBAXDT_data();
    public ODBAXDT_data data42 = new ODBAXDT_data();
    public ODBAXDT_data data43 = new ODBAXDT_data();
    public ODBAXDT_data data44 = new ODBAXDT_data();
    public ODBAXDT_data data45 = new ODBAXDT_data();
    public ODBAXDT_data data46 = new ODBAXDT_data();
    public ODBAXDT_data data47 = new ODBAXDT_data();
    public ODBAXDT_data data48 = new ODBAXDT_data();
    public ODBAXDT_data data49 = new ODBAXDT_data();
    public ODBAXDT_data data50 = new ODBAXDT_data();
    public ODBAXDT_data data51 = new ODBAXDT_data();
    public ODBAXDT_data data52 = new ODBAXDT_data();
    public ODBAXDT_data data53 = new ODBAXDT_data();
    public ODBAXDT_data data54 = new ODBAXDT_data();
    public ODBAXDT_data data55 = new ODBAXDT_data();
    public ODBAXDT_data data56 = new ODBAXDT_data();
    public ODBAXDT_data data57 = new ODBAXDT_data();
    public ODBAXDT_data data58 = new ODBAXDT_data();
    public ODBAXDT_data data59 = new ODBAXDT_data();
    public ODBAXDT_data data60 = new ODBAXDT_data();
    public ODBAXDT_data data61 = new ODBAXDT_data();
    public ODBAXDT_data data62 = new ODBAXDT_data();
    public ODBAXDT_data data63 = new ODBAXDT_data();
    public ODBAXDT_data data64 = new ODBAXDT_data();
    public ODBAXDT_data data65 = new ODBAXDT_data();
    public ODBAXDT_data data66 = new ODBAXDT_data();
    public ODBAXDT_data data67 = new ODBAXDT_data();
    public ODBAXDT_data data68 = new ODBAXDT_data();
    public ODBAXDT_data data69 = new ODBAXDT_data();
    public ODBAXDT_data data70 = new ODBAXDT_data();
    public ODBAXDT_data data71 = new ODBAXDT_data();
    public ODBAXDT_data data72 = new ODBAXDT_data();
    public ODBAXDT_data data73 = new ODBAXDT_data();
    public ODBAXDT_data data74 = new ODBAXDT_data();
    public ODBAXDT_data data75 = new ODBAXDT_data();
    public ODBAXDT_data data76 = new ODBAXDT_data();
    public ODBAXDT_data data77 = new ODBAXDT_data();
    public ODBAXDT_data data78 = new ODBAXDT_data();
    public ODBAXDT_data data79 = new ODBAXDT_data();
    public ODBAXDT_data data80 = new ODBAXDT_data();
    public ODBAXDT_data data81 = new ODBAXDT_data();
    public ODBAXDT_data data82 = new ODBAXDT_data();
    public ODBAXDT_data data83 = new ODBAXDT_data();
    public ODBAXDT_data data84 = new ODBAXDT_data();
    public ODBAXDT_data data85 = new ODBAXDT_data();
    public ODBAXDT_data data86 = new ODBAXDT_data();
    public ODBAXDT_data data87 = new ODBAXDT_data();
    public ODBAXDT_data data88 = new ODBAXDT_data();
    public ODBAXDT_data data89 = new ODBAXDT_data();
    public ODBAXDT_data data90 = new ODBAXDT_data();
    public ODBAXDT_data data91 = new ODBAXDT_data();
    public ODBAXDT_data data92 = new ODBAXDT_data();
    public ODBAXDT_data data93 = new ODBAXDT_data();
    public ODBAXDT_data data94 = new ODBAXDT_data();
    public ODBAXDT_data data95 = new ODBAXDT_data();
    public ODBAXDT_data data96 = new ODBAXDT_data();
    public ODBAXDT_data data97 = new ODBAXDT_data();
    public ODBAXDT_data data98 = new ODBAXDT_data();
    public ODBAXDT_data data99 = new ODBAXDT_data();
    public ODBAXDT_data data100 = new ODBAXDT_data();
    public ODBAXDT_data data101 = new ODBAXDT_data();
    public ODBAXDT_data data102 = new ODBAXDT_data();
    public ODBAXDT_data data103 = new ODBAXDT_data();
    public ODBAXDT_data data104 = new ODBAXDT_data();
    public ODBAXDT_data data105 = new ODBAXDT_data();
    public ODBAXDT_data data106 = new ODBAXDT_data();
    public ODBAXDT_data data107 = new ODBAXDT_data();
    public ODBAXDT_data data108 = new ODBAXDT_data();
    public ODBAXDT_data data109 = new ODBAXDT_data();
    public ODBAXDT_data data110 = new ODBAXDT_data();
    public ODBAXDT_data data111 = new ODBAXDT_data();
    public ODBAXDT_data data112 = new ODBAXDT_data();
    public ODBAXDT_data data113 = new ODBAXDT_data();
    public ODBAXDT_data data114 = new ODBAXDT_data();
    public ODBAXDT_data data115 = new ODBAXDT_data();
    public ODBAXDT_data data116 = new ODBAXDT_data();
    public ODBAXDT_data data117 = new ODBAXDT_data();
    public ODBAXDT_data data118 = new ODBAXDT_data();
    public ODBAXDT_data data119 = new ODBAXDT_data();
    public ODBAXDT_data data120 = new ODBAXDT_data();
    public ODBAXDT_data data121 = new ODBAXDT_data();
    public ODBAXDT_data data122 = new ODBAXDT_data();
    public ODBAXDT_data data123 = new ODBAXDT_data();
    public ODBAXDT_data data124 = new ODBAXDT_data();
    public ODBAXDT_data data125 = new ODBAXDT_data();
    public ODBAXDT_data data126 = new ODBAXDT_data();
    public ODBAXDT_data data127 = new ODBAXDT_data();
    public ODBAXDT_data data128 = new ODBAXDT_data();
  }

  /* cnc_rdspcss:read constant surface speed data */
  public static class ODBCSS extends JnaStructure {
    public int srpm; // order spindle speed
    public int sspm; // order constant spindle speed
    public int smax; // order maximum spindle speed
  }

  /* cnc_rdpdf_drive:read program drive directory */
  public static class ODBPDFDRV extends JnaStructure {
    public short max_num; // maximum drive number
    public short dummy;
    public String drive1 = StringHelper.repeatChar(' ', 12);
    public String drive2 = StringHelper.repeatChar(' ', 12);
    public String drive3 = StringHelper.repeatChar(' ', 12);
    public String drive4 = StringHelper.repeatChar(' ', 12);
    public String drive5 = StringHelper.repeatChar(' ', 12);
    public String drive6 = StringHelper.repeatChar(' ', 12);
    public String drive7 = StringHelper.repeatChar(' ', 12);
    public String drive8 = StringHelper.repeatChar(' ', 12);
    public String drive9 = StringHelper.repeatChar(' ', 12);
    public String drive10 = StringHelper.repeatChar(' ', 12);
    public String drive11 = StringHelper.repeatChar(' ', 12);
    public String drive12 = StringHelper.repeatChar(' ', 12);
    public String drive13 = StringHelper.repeatChar(' ', 12);
    public String drive14 = StringHelper.repeatChar(' ', 12);
    public String drive15 = StringHelper.repeatChar(' ', 12);
    public String drive16 = StringHelper.repeatChar(' ', 12);
  }

  /* cnc_rdpdf_inf:read program drive information */
  public static class ODBPDFINF extends JnaStructure {
    public int used_page; // used capacity
    public int all_page; // all capacity
    public int used_dir; // used directory number
    public int all_dir; // all directory number
  }

  /* cnc_rdpdf_subdir:read directory (sub directories) */
  public static class IDBPDFSDIR extends JnaStructure {
    public String path = StringHelper.repeatChar(' ', 212); // path name
    public short req_num; // entry number
    public short dummy;
  }

  /* cnc_rdpdf_subdir:read directory (sub directories) */
  public static class ODBPDFSDIR extends JnaStructure {
    public short sub_exist; // existence of sub directory
    public short dummy;
    public String d_f = StringHelper.repeatChar(' ', 36); // path name
  }

  /* cnc_rdpdf_alldir:read directory (all files) */
  public static class IDBPDFADIR extends JnaStructure {
    public String path = StringHelper.repeatChar(' ', 212); // path name
    public short req_num; // entry number
    public short size_kind; // kind of size
    public short type; // kind of format
    public short dummy;
  }

  /* cnc_rdpdf_alldir:read directory (all files) */
  public static class ODBPDFADIR extends JnaStructure {
    public short data_kind; // kinf of data
    public short year; // last date and time
    public short mon; // last date and time
    public short day; // last date and time
    public short hour; // last date and time
    public short min; // last date and time
    public short sec; // last date and time
    public short dummy;
    public int dummy2;
    public int size; // size
    public int attr; // attribute
    public String d_f = StringHelper.repeatChar(' ', 36); // path name
    public String comment = StringHelper.repeatChar(' ', 52); // comment

    public String o_time = StringHelper.repeatChar(' ', 12); // machining time stamp
  }

  /* cnc_rdpdf_subdirn:read file count the directory has */
  public static class ODBPDFNFIL extends JnaStructure {
    public short dir_num; // directory
    public short file_num; // file
  }

  /* cnc_wrpdf_attr:change attribute of program file and directory */
  public static class IDBPDFTDIR extends JnaStructure {
    public int slct; // selectio
    public int attr; // data
  }

  /*---------------------------*/
  /* CNC: NC file data related */
  /*---------------------------*/
  /* cnc_rdtofs:read tool offset value */
  public static class ODBTOFS extends JnaStructure {
    public short datano; // data number
    public short type; // data type
    public int data; // data
  }

  /* cnc_rdtofsr:read tool offset value(area specified) */
  /* cnc_wrtofsr:write tool offset value(area specified) */
  public static class OFS_1 extends JnaStructure {
    public int[] m_ofs = new int[5]; // M Each
    public int[] m_ofs_a = new int[5]; // M-A All
    public short[] t_tip = new short[5]; // T Each, 2-byte
    public int[] t_ofs = new int[5]; // T Each, 4-byte
  } // In case that the number of data is 5

  public static class OFS_2 extends JnaStructure {
    public int[] m_ofs_b = new int[10]; // M-B All
  } // In case that the number of data is 5

  public static class OFS_3 extends JnaStructure {
    public int[] m_ofs_c = new int[20]; // M-C All
  } // In case that the number of data is 5

  public static class T_OFS_A extends JnaStructure {
    public short tip;
    public int[] data = new int[4];
  } // T-A All

  public static class T_OFS_A_data extends JnaStructure {
    public T_OFS_A data1 = new T_OFS_A();
    public T_OFS_A data2 = new T_OFS_A();
    public T_OFS_A data3 = new T_OFS_A();
    public T_OFS_A data4 = new T_OFS_A();
    public T_OFS_A data5 = new T_OFS_A();
  } // In case that the number of data is 5

  public static class T_OFS_B extends JnaStructure {
    public short tip;

    public int[] data = new int[8];
  } // T-B All

  public static class T_OFS_B_data extends JnaStructure {
    public T_OFS_B data1 = new T_OFS_B();
    public T_OFS_B data2 = new T_OFS_B();
    public T_OFS_B data3 = new T_OFS_B();
    public T_OFS_B data4 = new T_OFS_B();
    public T_OFS_B data5 = new T_OFS_B();
  } // In case that the number of data is 5

  public static class IODBTO_1_1 extends JnaStructure {
    public short datano_s; // start offset number
    public short type; // offset type
    public short datano_e; // end offset number
    public OFS_1 ofs = new OFS_1();
  }

  public static class IODBTO_1_2 extends JnaStructure {
    public short datano_s; // start offset number
    public short type; // offset type
    public short datano_e; // end offset number
    public OFS_2 ofs = new OFS_2();
  }

  public static class IODBTO_1_3 extends JnaStructure {
    public short datano_s; // start offset number
    public short type; // offset type
    public short datano_e; // end offset number
    public OFS_3 ofs = new OFS_3();
  }

  public static class IODBTO_2 extends JnaStructure {
    public short datano_s; // start offset number
    public short type; // offset type
    public short datano_e; // end offset number
    public T_OFS_A_data tofsa = new T_OFS_A_data();
  }

  public static class IODBTO_3 extends JnaStructure {
    public short datano_s; // start offset number
    public short type; // offset type
    public short datano_e; // end offset number
    public T_OFS_B_data tofsb = new T_OFS_B_data();
  }

  /* cnc_rdzofs:read work zero offset value */
  /* cnc_wrzofs:write work zero offset value */
  public static class IODBZOFS extends JnaStructure {
    public short datano; // offset NO.
    public short type; // axis number

    public int[] data = new int[MAX_AXIS]; // data value
  }

  /* cnc_rdzofsr:read work zero offset value(area specified) */
  /* cnc_wrzofsr:write work zero offset value(area specified) */
  public static class IODBZOR extends JnaStructure {
    public short datano_s; // start offset number
    public short type; // axis number
    public short datano_e; // end offset number

    public int[] data = new int[7 * MAX_AXIS]; // offset value
  } // In case that the number of axes is MAX_AXIS, the number of data is 7

  /* cnc_rdmsptype:read mesured point value */
  /* cnc_wrmsptype:write mesured point value */
  public static class IODBMSTP extends JnaStructure {
    public short datano_s; // start offset number
    public short dummy; // dummy
    public short datano_e; // end offset number

    public byte[] data = new byte[7]; // mesured point value
  }

  /* cnc_rdparam:read parameter */
  /* cnc_wrparam:write parameter */
  /* cnc_rdset:read setting data */
  /* cnc_wrset:write setting data */
  /* cnc_rdparar:read parameter(area specified) */
  /* cnc_wrparas:write parameter(plural specified) */
  /* cnc_rdsetr:read setting data(area specified) */
  /* cnc_wrsets:write setting data(plural specified) */
  public static class IODBPSD extends JnaStructure {
    public short datano;    /* data number */
    public short type;      /* axis number */
    public IODBPSD_U u = new IODBPSD_U();
  }

  public static class IODBPSD_U extends JnaStructure {
    public byte[] cdatas = new byte[MAX_AXIS];
    public short[] idatas = new short[MAX_AXIS];
    public int[] ldatas = new int[MAX_AXIS];
    public REALPRM[] rdatas = new REALPRM[MAX_AXIS];
    public byte cdata;
    public short idata;
    public int ldata;
    public REALPRM rdata = new REALPRM();
  }
  public static class REALPRM extends JnaStructure {
    public int prm_val; // data of real parameter
    public int dec_val; // decimal point of real parameter
  }

  //#if M_AXIS2
  public static class REALPRMS extends JnaStructure {
    public REALPRM rdata1 = new REALPRM();
    public REALPRM rdata2 = new REALPRM();
    public REALPRM rdata3 = new REALPRM();
    public REALPRM rdata4 = new REALPRM();
    public REALPRM rdata5 = new REALPRM();
    public REALPRM rdata6 = new REALPRM();
    public REALPRM rdata7 = new REALPRM();
    public REALPRM rdata8 = new REALPRM();
  } // In case that the number of alarm is 24

  public static class IODBPSD_1 extends JnaStructure {
    public short datano; // data number
    public short type; // axis number
    public byte cdata; // parameter / setting data
    public short idata;
    public int ldata;
  }

  public static class IODBPSD_2 extends JnaStructure {
    public short datano; // data number
    public short type; // axis number
    public REALPRM rdata = new REALPRM();
  }

  public static class IODBPSD_3 extends JnaStructure {
    public short datano; // data number
    public short type; // axis number
    public byte[] cdatas = new byte[MAX_AXIS];
    public short[] idatas = new short[MAX_AXIS];
    public int[] ldatas = new int[MAX_AXIS];
  }

  public static class IODBPSD_4 extends JnaStructure {
    public short datano; // data number
    public short type; // axis number
    public REALPRMS rdatas = new REALPRMS();
  }

  public static class IODBPSD_A extends JnaStructure {
    public IODBPSD_1 data1 = new IODBPSD_1();
    public IODBPSD_1 data2 = new IODBPSD_1();
    public IODBPSD_1 data3 = new IODBPSD_1();
    public IODBPSD_1 data4 = new IODBPSD_1();
    public IODBPSD_1 data5 = new IODBPSD_1();
    public IODBPSD_1 data6 = new IODBPSD_1();
    public IODBPSD_1 data7 = new IODBPSD_1();
  } // (sample) must be modified

  public static class IODBPSD_B extends JnaStructure {
    public IODBPSD_2 data1 = new IODBPSD_2();
    public IODBPSD_2 data2 = new IODBPSD_2();
    public IODBPSD_2 data3 = new IODBPSD_2();
    public IODBPSD_2 data4 = new IODBPSD_2();
    public IODBPSD_2 data5 = new IODBPSD_2();
    public IODBPSD_2 data6 = new IODBPSD_2();
    public IODBPSD_2 data7 = new IODBPSD_2();
  } // (sample) must be modified

  public static class IODBPSD_C extends JnaStructure {
    public IODBPSD_3 data1 = new IODBPSD_3();
    public IODBPSD_3 data2 = new IODBPSD_3();
    public IODBPSD_3 data3 = new IODBPSD_3();
    public IODBPSD_3 data4 = new IODBPSD_3();
    public IODBPSD_3 data5 = new IODBPSD_3();
    public IODBPSD_3 data6 = new IODBPSD_3();
    public IODBPSD_3 data7 = new IODBPSD_3();
  } // (sample) must be modified

  public static class IODBPSD_D extends JnaStructure {
    public IODBPSD_4 data1 = new IODBPSD_4();
    public IODBPSD_4 data2 = new IODBPSD_4();
    public IODBPSD_4 data3 = new IODBPSD_4();
    public IODBPSD_4 data4 = new IODBPSD_4();
    public IODBPSD_4 data5 = new IODBPSD_4();
    public IODBPSD_4 data6 = new IODBPSD_4();
    public IODBPSD_4 data7 = new IODBPSD_4();
  } // (sample) must be modified

  /* cnc_rdparam_ext:read parameters */
  /* cnc_rddiag_ext:read diagnosis data */
  /* cnc_start_async_wrparam:async parameter write start */
  public static class IODBPRMNO extends JnaStructure {
    public int[] prm = new int[10];
  }

  public static class IODBPRM_data extends JnaStructure {
    public int prm_val; // parameter / setting data
    public int dec_val;
  }

  public static class IODBPRM1 extends JnaStructure {
    public IODBPRM_data data1 = new IODBPRM_data();
    public IODBPRM_data data2 = new IODBPRM_data();
    public IODBPRM_data data3 = new IODBPRM_data();
    public IODBPRM_data data4 = new IODBPRM_data();
    public IODBPRM_data data5 = new IODBPRM_data();
    public IODBPRM_data data6 = new IODBPRM_data();
    public IODBPRM_data data7 = new IODBPRM_data();
    public IODBPRM_data data8 = new IODBPRM_data();
    public IODBPRM_data data9 = new IODBPRM_data();
    public IODBPRM_data data10 = new IODBPRM_data();
    public IODBPRM_data data11 = new IODBPRM_data();
    public IODBPRM_data data12 = new IODBPRM_data();
    public IODBPRM_data data13 = new IODBPRM_data();
    public IODBPRM_data data14 = new IODBPRM_data();
    public IODBPRM_data data15 = new IODBPRM_data();
    public IODBPRM_data data16 = new IODBPRM_data();
    public IODBPRM_data data17 = new IODBPRM_data();
    public IODBPRM_data data18 = new IODBPRM_data();
    public IODBPRM_data data19 = new IODBPRM_data();
    public IODBPRM_data data20 = new IODBPRM_data();
    public IODBPRM_data data21 = new IODBPRM_data();
    public IODBPRM_data data22 = new IODBPRM_data();
    public IODBPRM_data data23 = new IODBPRM_data();
    public IODBPRM_data data24 = new IODBPRM_data();
    public IODBPRM_data data25 = new IODBPRM_data();
    public IODBPRM_data data26 = new IODBPRM_data();
    public IODBPRM_data data27 = new IODBPRM_data();
    public IODBPRM_data data28 = new IODBPRM_data();
    public IODBPRM_data data29 = new IODBPRM_data();
    public IODBPRM_data data30 = new IODBPRM_data();
    public IODBPRM_data data31 = new IODBPRM_data();
    public IODBPRM_data data32 = new IODBPRM_data();
  }

  public static class IODBPRM2 extends JnaStructure {
    public int datano; // data number
    public short type; // data type
    public short axis; // axis information
    public short info; // misc information
    public short unit; // unit information
    public IODBPRM1 data = new IODBPRM1();
  }

  public static class IODBPRM extends JnaStructure {
    public IODBPRM2 prm1 = new IODBPRM2();
    public IODBPRM2 prm2 = new IODBPRM2();
    public IODBPRM2 prm3 = new IODBPRM2();
    public IODBPRM2 prm4 = new IODBPRM2();
    public IODBPRM2 prm5 = new IODBPRM2();
    public IODBPRM2 prm6 = new IODBPRM2();
    public IODBPRM2 prm7 = new IODBPRM2();
    public IODBPRM2 prm8 = new IODBPRM2();
    public IODBPRM2 prm9 = new IODBPRM2();
    public IODBPRM2 prm10 = new IODBPRM2();
  } // In case that the number of alarm is 10

  /* cnc_rdpitchr:read pitch error compensation data(area specified) */
  /* cnc_wrpitchr:write pitch error compensation data(area specified) */
  public static class IODBPI extends JnaStructure {
    public short datano_s; // start pitch number
    public short dummy; // dummy
    public short datano_e; // end pitch number
    public byte[] data = new byte[5]; // offset value
  } // In case that the number of data is 5

  /* cnc_rdmacro:read custom macro variable */
  public static class ODBM extends JnaStructure {
    public short datano; // variable number
    public short dummy; // dummy
    public int mcr_val; // macro variable
    public short dec_val; // decimal point
  }

  /* cnc_rdmacror:read custom macro variables(area specified) */
  /* cnc_wrmacror:write custom macro variables(area specified) */
  public static class IODBMR_data extends JnaStructure {
    public int mcr_val; // macro variable
    public short dec_val; // decimal point
  }

  public static class IODBMR1 extends JnaStructure {
    public IODBMR_data data1 = new IODBMR_data();
    public IODBMR_data data2 = new IODBMR_data();
    public IODBMR_data data3 = new IODBMR_data();
    public IODBMR_data data4 = new IODBMR_data();
    public IODBMR_data data5 = new IODBMR_data();
  } // In case that the number of data is 5

  public static class IODBMR extends JnaStructure {
    public short datano_s; // start macro number
    public short dummy; // dummy
    public short datano_e; // end macro number
    public IODBMR1 data = new IODBMR1();
  }

  /* cnc_rdpmacro:read P code macro variable */
  public static class ODBPM extends JnaStructure {
    public int datano; // variable number
    public short dummy; // dummy
    public int mcr_val; // macro variable
    public short dec_val; // decimal point
  }

  /* cnc_rdpmacror:read P code macro variables(area specified) */
  /* cnc_wrpmacror:write P code macro variables(area specified) */
  public static class IODBPR_data extends JnaStructure {
    public int mcr_val; // macro variable
    public short dec_val; // decimal point
  }

  public static class IODBPR1 extends JnaStructure {
    public IODBPR_data data1 = new IODBPR_data();
    public IODBPR_data data2 = new IODBPR_data();
    public IODBPR_data data3 = new IODBPR_data();
    public IODBPR_data data4 = new IODBPR_data();
    public IODBPR_data data5 = new IODBPR_data();
  } // In case that the number of data is 5

  public static class IODBPR extends JnaStructure {
    public int datano_s; // start macro number
    public short dummy; // dummy
    public int datano_e; // end macro number
    public IODBPR1 data = new IODBPR1();
  }

  /* cnc_rdtofsinfo:read tool offset information */
  public static class ODBTLINF extends JnaStructure {
    public short ofs_type;
    public short use_no;
  }

  /* cnc_rdtofsinfo2:read tool offset information(2) */
  public static class ODBTLINF2 extends JnaStructure {
    public short ofs_type;
    public short use_no;
    public short ofs_enable;
  }

  /* cnc_rdmacroinfo:read custom macro variable information */
  public static class ODBMVINF extends JnaStructure {
    public short use_no1;
    public short use_no2;
  }

  /* cnc_rdpmacroinfo:read P code macro variable information */
  public static class ODBPMINF extends JnaStructure {
    public short use_no1;
    public short use_no2;
    public short v2_type;
  }

  /* cnc_tofs_rnge:read validity of tool offset */
  /* cnc_zofs_rnge:read validity of work zero offset */
  public static class ODBDATRNG extends JnaStructure {
    public int data_min; // lower limit
    public int data_max; // upper limit
    public int status; // status of setting
  }

  /* cnc_rdhsprminfo:read the information for function cnc_rdhsparam() */
  public static class HSPINFO_data extends JnaStructure {
    public byte[] data1 = new byte[16];
    public byte[] data2 = new byte[16];
    public byte[] data3 = new byte[16];
    public byte[] data4 = new byte[16];
    public byte[] data5 = new byte[16];
    public byte[] data6 = new byte[16];
    public byte[] data7 = new byte[16];
    public byte[] data8 = new byte[16];
  }

  public static class HSPINFO extends JnaStructure {
    public HSPINFO_data prminfo1 = new HSPINFO_data();
    public HSPINFO_data prminfo2 = new HSPINFO_data();
    public HSPINFO_data prminfo3 = new HSPINFO_data();
    public HSPINFO_data prminfo4 = new HSPINFO_data();
    public HSPINFO_data prminfo5 = new HSPINFO_data();
    public HSPINFO_data prminfo6 = new HSPINFO_data();
    public HSPINFO_data prminfo7 = new HSPINFO_data();
    public HSPINFO_data prminfo8 = new HSPINFO_data();
    public HSPINFO_data prminfo9 = new HSPINFO_data();
    public HSPINFO_data prminfo10 = new HSPINFO_data();
  }

  /* cnc_rdhsparam:read parameters at the high speed */
  public static class HSPDATA_1 extends JnaStructure {
    public byte[] cdatas1 = new byte[MAX_AXIS];
    public byte[] cdatas2 = new byte[MAX_AXIS];
    public byte[] cdatas3 = new byte[MAX_AXIS];
    public byte[] cdatas4 = new byte[MAX_AXIS];
    public byte[] cdatas5 = new byte[MAX_AXIS];
    public byte[] cdatas6 = new byte[MAX_AXIS];
    public byte[] cdatas7 = new byte[MAX_AXIS];
    public byte[] cdatas8 = new byte[MAX_AXIS];
    public byte[] cdatas9 = new byte[MAX_AXIS];
    public byte[] cdatas10 = new byte[MAX_AXIS];
  }

  public static class HSPDATA_2 {

    public short[] idatas1 = new short[MAX_AXIS];

    public short[] idatas2 = new short[MAX_AXIS];

    public short[] idatas3 = new short[MAX_AXIS];

    public short[] idatas4 = new short[MAX_AXIS];

    public short[] idatas5 = new short[MAX_AXIS];

    public short[] idatas6 = new short[MAX_AXIS];

    public short[] idatas7 = new short[MAX_AXIS];

    public short[] idatas8 = new short[MAX_AXIS];

    public short[] idatas9 = new short[MAX_AXIS];

    public short[] idatas10 = new short[MAX_AXIS];
  }

  public static class HSPDATA_3 extends JnaStructure {

    public int[] ldatas1 = new int[MAX_AXIS];

    public int[] ldatas2 = new int[MAX_AXIS];

    public int[] ldatas3 = new int[MAX_AXIS];

    public int[] ldatas4 = new int[MAX_AXIS];

    public int[] ldatas5 = new int[MAX_AXIS];

    public int[] ldatas6 = new int[MAX_AXIS];

    public int[] ldatas7 = new int[MAX_AXIS];

    public int[] ldatas8 = new int[MAX_AXIS];

    public int[] ldatas9 = new int[MAX_AXIS];

    public int[] ldatas10 = new int[MAX_AXIS];
  }

  /*----------------------------------------*/
  /* CNC: Tool life management data related */
  /*----------------------------------------*/
  /* cnc_rdgrpid:read tool life management data(tool group number) */
  public static class ODBTLIFE1 extends JnaStructure {
    public short dummy; // dummy
    public short type; // data type
    public int data; // data
  }

  /* cnc_rdngrp:read tool life management data(number of tool groups) */
  public static class ODBTLIFE2 extends JnaStructure {
    public short[] dummy = new short[2]; // dummy
    public int data; // data
  }

  /* cnc_rdntool:read tool life management data(number of utils) */
  /* cnc_rdlife:read tool life management data(tool life) */
  /* cnc_rdcount:read tool life management data(tool lift counter) */
  public static class ODBTLIFE3 extends JnaStructure {
    public short datano; // data number
    public short dummy; // dummy
    public int data; // data
  }

  /* cnc_rd1length:read tool life management data(tool length number-1) */
  /* cnc_rd2length:read tool life management data(tool length number-2) */
  /* cnc_rd1radius:read tool life management data(cutter compensation no.-1) */
  /* cnc_rd2radius:read tool life management data(cutter compensation no.-2) */
  /* cnc_t1info:read tool life management data(tool information-1) */
  /* cnc_t2info:read tool life management data(tool information-2) */
  /* cnc_toolnum:read tool life management data(tool number) */
  public static class ODBTLIFE4 extends JnaStructure {
    public short datano; // data number
    public short type; // data type
    public int data; // data
  }

  /* cnc_rdgrpid2:read tool life management data(tool group number) 2 */
  public static class ODBTLIFE5 extends JnaStructure {
    public int dummy; // dummy
    public int type; // data type
    public int data; // data
  }

  /* cnc_rdtoolrng:read tool life management data(tool number, tool life, tool life counter)(area specified) */
  public static class IODBTR_data extends JnaStructure {
    public int ntool; // tool number
    public int life; // tool life
    public int count; // tool life counter
  }

  public static class IODBTR1 extends JnaStructure {
    public IODBTR_data data1 = new IODBTR_data();
    public IODBTR_data data2 = new IODBTR_data();
    public IODBTR_data data3 = new IODBTR_data();
    public IODBTR_data data4 = new IODBTR_data();
    public IODBTR_data data5 = new IODBTR_data();
  } // In case that the number of data is 5

  public static class IODBTR extends JnaStructure {
    public short datano_s; // start group number
    public short dummy; // dummy
    public short datano_e; // end group number
    public IODBTR1 data = new IODBTR1();
  }

  /* cnc_rdtoolgrp:read tool life management data(all data within group) */
  public static class ODBTG_data extends JnaStructure {
    public int tuse_num; // tool number
    public int tool_num; // tool life
    public int length_num; // tool life counter
    public int radius_num; // tool life counter
    public int tinfo; // tool life counter
  }

  public static class ODBTG1 extends JnaStructure {
    public ODBTG_data data1 = new ODBTG_data();
    public ODBTG_data data2 = new ODBTG_data();
    public ODBTG_data data3 = new ODBTG_data();
    public ODBTG_data data4 = new ODBTG_data();
    public ODBTG_data data5 = new ODBTG_data();
  } // In case that the number of data is 5

  public static class ODBTG extends JnaStructure {
    public short grp_num; // start group number
    public short[] dummy = new short[2]; // dummy
    public int ntool; // tool number
    public int life; // tool life
    public int count; // tool life counter
    public ODBTG1 data = new ODBTG1();
  }

  /* cnc_wrcountr:write tool life management data(tool life counter) (area specified) */
  public static class IDBWRC_data extends JnaStructure {
    public int[] dummy = new int[2]; // dummy
    public int count; // tool life counter
  }

  public static class IDBWRC1 extends JnaStructure {
    public IDBWRC_data data1 = new IDBWRC_data();
    public IDBWRC_data data2 = new IDBWRC_data();
    public IDBWRC_data data3 = new IDBWRC_data();
    public IDBWRC_data data4 = new IDBWRC_data();
    public IDBWRC_data data5 = new IDBWRC_data();
  } // In case that the number of data is 5

  public static class IDBWRC extends JnaStructure {
    public short datano_s; // start group number
    public short dummy; // dummy
    public short datano_e; // end group number
    public IDBWRC1 data = new IDBWRC1();
  }

  /* cnc_rdusegrpid:read tool life management data(used tool group number) */
  public static class ODBUSEGR extends JnaStructure {
    public short datano; // dummy
    public short type; // dummy
    public int next; // next use group number
    public int use; // using group number
    public int slct; // selecting group number
  }

  /* cnc_rdmaxgrp:read tool life management data(max. number of tool groups) */
  /* cnc_rdmaxtool:read tool life management data(maximum number of tool within group) */
  public static class ODBLFNO extends JnaStructure {
    public short datano; // dummy
    public short type; // dummy
    public short data; // number of data
  }

  /* cnc_rdusetlno:read tool life management data(used tool no within group) */
  public static class ODBTLUSE extends JnaStructure {
    public short s_grp; // start group number
    public short dummy; // dummy
    public short e_grp; // end group number

    public int[] data = new int[5]; // tool using number
  } // In case that the number of group is 5

  /* cnc_rd1tlifedata:read tool life management data(tool data1) */
  /* cnc_rd2tlifedata:read tool life management data(tool data2) */
  /* cnc_wr1tlifedata:write tool life management data(tool data1) */
  /* cnc_wr2tlifedata:write tool life management data(tool data2) */
  public static class IODBTD extends JnaStructure {
    public short datano; // tool group number
    public short type; // tool using number
    public int tool_num; // tool number
    public int h_code; // H code
    public int d_code; // D code
    public int tool_inf; // tool information
  }

  /* cnc_rd1tlifedat2:read tool life management data(tool data1) 2 */
  /* cnc_wr1tlifedat2:write tool life management data(tool data1) 2 */
  public static class IODBTD2 extends JnaStructure {
    public short datano; // tool group number
    public short dummy; // dummy
    public int type; // tool using number
    public int tool_num; // tool number
    public int h_code; // H code
    public int d_code; // D code
    public int tool_inf; // tool information
  }

  /* cnc_rdgrpinfo:read tool life management data(tool group information) */
  /* cnc_wrgrpinfo:write tool life management data(tool group information) */
  public static class IODBTGI_data extends JnaStructure {
    public int n_tool; // number of tool
    public int count_value; // tool life
    public int counter; // tool life counter
    public int count_type; // tool life counter type
  }

  public static class IODBTGI1 extends JnaStructure {
    public IODBTGI_data data1 = new IODBTGI_data();
    public IODBTGI_data data2 = new IODBTGI_data();
    public IODBTGI_data data3 = new IODBTGI_data();
    public IODBTGI_data data4 = new IODBTGI_data();
    public IODBTGI_data data5 = new IODBTGI_data();
  } // In case that the number of data is 5

  public static class IODBTGI extends JnaStructure {
    public short s_grp; // start group number
    public short dummy; // dummy
    public short e_grp; // end group number
    public IODBTGI1 data = new IODBTGI1();
  }

  /* cnc_rdgrpinfo2:read tool life management data(tool group information 2) */
  /* cnc_wrgrpinfo2:write tool life management data(tool group information 2) */
  public static class IODBTGI2 extends JnaStructure {
    public short s_grp; // start group number
    public short dummy; // dummy
    public short e_grp; // end group number

    public int[] opt_grpno = new int[5]; // optional group number of tool
  } // In case that the number of group is 5

  /* cnc_rdgrpinfo3:read tool life management data(tool group information 3) */
  /* cnc_wrgrpinfo3:write tool life management data(tool group information 3) */
  public static class IODBTGI3 extends JnaStructure {
    public short s_grp; // start group number
    public short dummy; // dummy
    public short e_grp; // end group number

    public int[] life_rest = new int[5]; // tool life rest count
  } // In case that the number of group is 5

  /* cnc_rdgrpinfo4:read tool life management data(tool group information 4) */
  public static class IODBTGI4 extends JnaStructure {
    public short grp_no;
    public int n_tool;
    public int count_value;
    public int counter;
    public int count_type;
    public int opt_grpno;
    public int life_rest;
  }

  /* cnc_instlifedt:insert tool life management data(tool data) */
  public static class IDBITD extends JnaStructure {
    public short datano; // tool group number
    public short type; // tool using number
    public int data; // tool number
  }

  /* cnc_rdtlinfo:read tool life management data */
  public static class ODBTLINFO extends JnaStructure {
    public int max_group; // maximum number of tool groups
    public int max_tool; // maximum number of tool within group
    public int max_minute; // maximum number of life count (minutes)
    public int max_cycle; // maximum number of life count (cycles)
  }

  /* cnc_rdtlusegrp:read tool life management data(used tool group number) */
  public static class ODBUSEGRP extends JnaStructure {
    public int next; // next use group number
    public int use; // using group number
    public int slct; // selecting group number
    public int opt_next; // next use optional group number
    public int opt_use; // using optional group number
    public int opt_slct; // selecting optional group number
  }

  /* cnc_rdtlgrp:read tool life management data(tool group information 2) */
  public static class IODBTLGRP_data extends JnaStructure {
    public int ntool; // number of all tool
    public int nfree; // number of free tool
    public int life; // tool life
    public int count; // tool life counter
    public int use_tool; // using tool number
    public int opt_grpno; // optional group number
    public int life_rest; // tool life rest count
    public short rest_sig; // tool life rest signal
    public short count_type; // tool life counter type
  }

  public static class IODBTLGRP extends JnaStructure {
    public IODBTLGRP_data data1 = new IODBTLGRP_data();
    public IODBTLGRP_data data2 = new IODBTLGRP_data();
    public IODBTLGRP_data data3 = new IODBTLGRP_data();
    public IODBTLGRP_data data4 = new IODBTLGRP_data();
    public IODBTLGRP_data data5 = new IODBTLGRP_data();
  } // In case that the number of group is 5

  /* cnc_rdtltool:read tool life management data (tool data1) */
  public static class IODBTLTOOL_data extends JnaStructure {
    public int tool_num; // tool number
    public int h_code; // H code
    public int d_code; // D code
    public int tool_inf; // tool information
  }

  public static class IODBTLTOOL extends JnaStructure {
    public IODBTLTOOL_data data1 = new IODBTLTOOL_data();
    public IODBTLTOOL_data data2 = new IODBTLTOOL_data();
    public IODBTLTOOL_data data3 = new IODBTLTOOL_data();
    public IODBTLTOOL_data data4 = new IODBTLTOOL_data();
    public IODBTLTOOL_data data5 = new IODBTLTOOL_data();
  } // In case that the number of group is 5

  public static class ODBEXGP_data extends JnaStructure {
    public int grp_no; // group number
    public int opt_grpno; // optional group number
  }

  public static class ODBEXGP extends JnaStructure {
    public ODBEXGP_data data1 = new ODBEXGP_data();
    public ODBEXGP_data data2 = new ODBEXGP_data();
    public ODBEXGP_data data3 = new ODBEXGP_data();
    public ODBEXGP_data data4 = new ODBEXGP_data();
    public ODBEXGP_data data5 = new ODBEXGP_data();
    public ODBEXGP_data data6 = new ODBEXGP_data();
    public ODBEXGP_data data7 = new ODBEXGP_data();
    public ODBEXGP_data data8 = new ODBEXGP_data();
    public ODBEXGP_data data9 = new ODBEXGP_data();
    public ODBEXGP_data data10 = new ODBEXGP_data();
    public ODBEXGP_data data11 = new ODBEXGP_data();
    public ODBEXGP_data data12 = new ODBEXGP_data();
    public ODBEXGP_data data13 = new ODBEXGP_data();
    public ODBEXGP_data data14 = new ODBEXGP_data();
    public ODBEXGP_data data15 = new ODBEXGP_data();
    public ODBEXGP_data data16 = new ODBEXGP_data();
    public ODBEXGP_data data17 = new ODBEXGP_data();
    public ODBEXGP_data data18 = new ODBEXGP_data();
    public ODBEXGP_data data19 = new ODBEXGP_data();
    public ODBEXGP_data data20 = new ODBEXGP_data();
    public ODBEXGP_data data21 = new ODBEXGP_data();
    public ODBEXGP_data data22 = new ODBEXGP_data();
    public ODBEXGP_data data23 = new ODBEXGP_data();
    public ODBEXGP_data data24 = new ODBEXGP_data();
    public ODBEXGP_data data25 = new ODBEXGP_data();
    public ODBEXGP_data data26 = new ODBEXGP_data();
    public ODBEXGP_data data27 = new ODBEXGP_data();
    public ODBEXGP_data data28 = new ODBEXGP_data();
    public ODBEXGP_data data29 = new ODBEXGP_data();
    public ODBEXGP_data data30 = new ODBEXGP_data();
    public ODBEXGP_data data31 = new ODBEXGP_data();
    public ODBEXGP_data data32 = new ODBEXGP_data();
  }

  /*-----------------------------------*/
  /* CNC: Tool management data related */
  /*-----------------------------------*/
  /* cnc_regtool:new registration of tool management data */
  /* cnc_rdtool:lead of tool management data */
  /* cnc_wrtool:write of tool management data */
  public static class IODBTLMNG_data extends JnaStructure {
    public int T_code;
    public int life_count;
    public int max_life;
    public int rest_life;

    public byte life_stat;

    public byte cust_bits;

    public short tool_info;
    public short H_code;
    public short D_code;
    public int spindle_speed;
    public int feedrate;
    public short magazine;
    public short pot;
    public short gno;
    public short m_ofs;

    public int[] reserved = new int[4];
    public int custom1;
    public int custom2;
    public int custom3;
    public int custom4;
    public int custom5;
    public int custom6;
    public int custom7;
    public int custom8;
    public int custom9;
    public int custom10;
    public int custom11;
    public int custom12;
    public int custom13;
    public int custom14;
    public int custom15;
    public int custom16;
    public int custom17;
    public int custom18;
    public int custom19;
    public int custom20;
  }

  public static class IODBTLMNG extends JnaStructure {
    public IODBTLMNG_data data1 = new IODBTLMNG_data();
    public IODBTLMNG_data data2 = new IODBTLMNG_data();
    public IODBTLMNG_data data3 = new IODBTLMNG_data();
    public IODBTLMNG_data data4 = new IODBTLMNG_data();
    public IODBTLMNG_data data5 = new IODBTLMNG_data();
  } // In case that the number of group is 5

  public static class IODBTLMNG_F2_data extends JnaStructure {
    public int T_code;
    public int life_count;
    public int max_life;
    public int rest_life;

    public byte life_stat;

    public byte cust_bits;

    public short tool_info;
    public short H_code;
    public short D_code;
    public int spindle_speed;
    public int feedrate;
    public short magazine;
    public short pot;
    public short G_code;
    public short W_code;
    public short gno;
    public short m_ofs;

    public int[] reserved = new int[3];
    public int custom1;
    public int custom2;
    public int custom3;
    public int custom4;
    public int custom5;
    public int custom6;
    public int custom7;
    public int custom8;
    public int custom9;
    public int custom10;
    public int custom11;
    public int custom12;
    public int custom13;
    public int custom14;
    public int custom15;
    public int custom16;
    public int custom17;
    public int custom18;
    public int custom19;
    public int custom20;
    public int custom21;
    public int custom22;
    public int custom23;
    public int custom24;
    public int custom25;
    public int custom26;
    public int custom27;
    public int custom28;
    public int custom29;
    public int custom30;
    public int custom31;
    public int custom32;
    public int custom33;
    public int custom34;
    public int custom35;
    public int custom36;
    public int custom37;
    public int custom38;
    public int custom39;
    public int custom40;
  }

  public static class IODBTLMNG_F2 extends JnaStructure {
    public IODBTLMNG_F2_data data1 = new IODBTLMNG_F2_data();
    public IODBTLMNG_F2_data data2 = new IODBTLMNG_F2_data();
    public IODBTLMNG_F2_data data3 = new IODBTLMNG_F2_data();
    public IODBTLMNG_F2_data data4 = new IODBTLMNG_F2_data();
    public IODBTLMNG_F2_data data5 = new IODBTLMNG_F2_data();
  } // In case that the number of group is 5

  /* cnc_wrtool2:write of individual data of tool management data */
  public static class IDBTLM_item extends JnaStructure {

    public byte data1;

    public short data2;

    public int data4;
  }

  public static class IDBTLM extends JnaStructure {
    public short data_id;
    public IDBTLM_item item = new IDBTLM_item();
  }

  /* cnc_regmagazine:new registration of magazine management data */
  /* cnc_rdmagazine:lead of magazine management data */
  public static class IODBTLMAG_data extends JnaStructure {
    public short magazine;
    public short pot;
    public short tool_index;
  }

  public static class IODBTLMAG extends JnaStructure {
    public IODBTLMAG_data data1 = new IODBTLMAG_data();
    public IODBTLMAG_data data2 = new IODBTLMAG_data();
    public IODBTLMAG_data data3 = new IODBTLMAG_data();
    public IODBTLMAG_data data4 = new IODBTLMAG_data();
    public IODBTLMAG_data data5 = new IODBTLMAG_data();
  } // In case that the number of group is 5

  /* cnc_delmagazine:deletion of magazine management data */
  public static class IODBTLMAG2_data extends JnaStructure {
    public short magazine;
    public short pot;
  }

  public static class IODBTLMAG2 extends JnaStructure {
    public IODBTLMAG2_data data1 = new IODBTLMAG2_data();
    public IODBTLMAG2_data data2 = new IODBTLMAG2_data();
    public IODBTLMAG2_data data3 = new IODBTLMAG2_data();
    public IODBTLMAG2_data data4 = new IODBTLMAG2_data();
    public IODBTLMAG2_data data5 = new IODBTLMAG2_data();
  } // In case that the number of group is 5

  /*-------------------------------------*/
  /* CNC: Operation history data related */
  /*-------------------------------------*/
  /* cnc_rdophistry:read operation history data */
  public static class REC_ALM extends JnaStructure {
    public short rec_type; // record type
    public short alm_grp; // alarm group
    public short alm_no; // alarm number
    public byte axis_no; // axis number
    public byte dummy;
  }

  public static class REC_MDI extends JnaStructure {
    public short rec_type; // record type

    public byte key_code; // key code

    public byte pw_flag; // power on flag

    public byte[] dummy = new byte[4];
  }

  public static class REC_SGN extends JnaStructure {
    public short rec_type; // record type
    public byte sig_name; // signal name

    public byte sig_old; // old signal bit pattern

    public byte sig_new; // new signal bit pattern
    public byte dummy;
    public short sig_no; // signal number
  }

  public static class REC_DATE extends JnaStructure {
    public short rec_type; // record type
    public byte year; // year
    public byte month; // month
    public byte day; // day
    public byte pw_flag; // power on flag

    public byte[] dummy = new byte[2];
  }

  public static class REC_TIME extends JnaStructure {
    public short rec_type; // record flag
    public byte hour; // hour
    public byte minute; // minute
    public byte second; // second
    public byte pw_flag; // power on flag

    public byte[] dummy = new byte[2];
  }

  public static class ODBHIS_data extends JnaStructure {
    // record type

    public short rec_type; // record type
    // alarm record

    public short alm_rec_type; // record type

    public short alm_alm_grp; // alarm group

    public short alm_alm_no; // alarm number

    public byte alm_axis_no; // axis number

    public byte alm_dummy;
    // mdi record

    public short mdi_rec_type; // record type

    public byte mdi_key_code; // key code

    public byte mdi_pw_flag; // power on flag

    public byte mdi_dummy1;

    public byte mdi_dummy2;

    public byte mdi_dummy3;

    public byte mdi_dummy4;
    // sign record

    public short sgn_rec_type; // record type

    public byte sgn_sig_name; // signal name

    public byte sgn_sig_old; // old signal bit pattern

    public byte sgn_sig_new; // new signal bit pattern

    public byte sgn_dummy;

    public short sgn_sig_no; // signal number
    // date record

    public short date_rec_type; // record type

    public byte date_year; // year

    public byte date_month; // month

    public byte date_day; // day

    public byte date_pw_flag; // power on flag

    public byte date_dummy1;

    public byte date_dummy2;
    // time record

    public short time_rec_type; // record flag

    public byte time_hour; // hour

    public byte time_minute; // minute

    public byte time_second; // second

    public byte time_pw_flag; // power on flag

    public byte time_dummy1;

    public byte time_dummy2;
  }

  public static class ODBHIS1 extends JnaStructure {
    public ODBHIS_data data1 = new ODBHIS_data();
    public ODBHIS_data data2 = new ODBHIS_data();
    public ODBHIS_data data3 = new ODBHIS_data();
    public ODBHIS_data data4 = new ODBHIS_data();
    public ODBHIS_data data5 = new ODBHIS_data();
    public ODBHIS_data data6 = new ODBHIS_data();
    public ODBHIS_data data7 = new ODBHIS_data();
    public ODBHIS_data data8 = new ODBHIS_data();
    public ODBHIS_data data9 = new ODBHIS_data();
    public ODBHIS_data data10 = new ODBHIS_data();
  } // In case that the number of data is 10

  public static class ODBHIS extends JnaStructure {

    public short s_no; // start number
    public short type; // dummy

    public short e_no; // end number
    public ODBHIS1 data = new ODBHIS1();
  }

  /* cnc_rdophistry2:read operation history data */
  public static class REC_MDI2 extends JnaStructure {

    public byte key_code; // key code

    public byte pw_flag; // power on flag
    public short dummy;
  }

  public static class REC_MDI2_data extends JnaStructure {
    public short rec_len; // length
    public short rec_type; // record type
    public REC_MDI2 data = new REC_MDI2();
  }

  public static class REC_SGN2 extends JnaStructure {
    public short sig_name; // signal name
    public short sig_no; // signal number

    public byte sig_old; // old signal bit pattern

    public byte sig_new; // new signal bit pattern
    public short dummy;
  }

  public static class REC_SGN2_data extends JnaStructure {
    public short rec_len; // length
    public short rec_type; // record type
    public REC_SGN2 data = new REC_SGN2();
  }

  public static class REC_ALM2 extends JnaStructure {
    public short alm_grp; // alarm group
    public short alm_no; // alarm number
    public short axis_no; // axis number
    public short year; // year
    public short month; // month
    public short day; // day
    public short hour; // hour
    public short minute; // minute
    public short second; // second
    public short dummy;
  }

  public static class REC_ALM2_data extends JnaStructure {
    public short rec_len; // length
    public short rec_type; // record type
    public REC_ALM2 data = new REC_ALM2();
  }

  public static class REC_DATE2 extends JnaStructure {
    public short evnt_type; // event type
    public short year; // year
    public short month; // month
    public short day; // day
    public short hour; // hour
    public short minute; // minute
    public short second; // second
    public short dummy;
  }

  public static class REC_DATE2_data extends JnaStructure {
    public short rec_len; // length
    public short rec_type; // record type
    public REC_DATE2 data = new REC_DATE2();
  }

  public static class ODBOPHIS extends JnaStructure {

    public REC_MDI2_data rec_mdi = new REC_MDI2_data();

    public REC_SGN2_data rec_sgn = new REC_SGN2_data();

    public REC_ALM2_data rec_alm = new REC_ALM2_data();

    public REC_DATE2_data rec_date = new REC_DATE2_data();
  }

  /* cnc_rdophistry4:read operation history data */
  public static class REC_MDI4 extends JnaStructure {
    public char key_code; // key code
    public char pw_flag; // power on flag
    public short pth_no; // path index
    public short ex_flag; // kxternal key flag
    public short hour; // hour
    public short minute; // minute
    public short second; // second
  }

  public static class REC_MDI4_data extends JnaStructure {
    public short rec_len; // length
    public short rec_type; // record type
    public REC_MDI4 data = new REC_MDI4();
  }

  public static class REC_SGN4 extends JnaStructure {
    public short sig_name; // signal name
    public short sig_no; // signal number
    public char sig_old; // old signal bit pattern
    public char sig_new; // new signal bit pattern
    public short pmc_no; // pmc index
    public short hour; // hour
    public short minute; // minute
    public short second; // second
    public short dummy;
  }

  public static class REC_SGN4_data extends JnaStructure {
    public short rec_len; // length
    public short rec_type; // record type
    public REC_SGN4 data = new REC_SGN4();
  }

  public static class REC_ALM4 extends JnaStructure {
    public short alm_grp; // alarm group
    public short alm_no; // alarm number
    public short axis_no; // axis number
    public short year; // year
    public short month; // month
    public short day; // day
    public short hour; // hour
    public short minute; // minute
    public short second; // second
    public short pth_no; // path index
  }

  public static class REC_ALM4_data extends JnaStructure {
    public short rec_len; // length
    public short rec_type; // record type
    public REC_ALM4 data = new REC_ALM4();
  }

  public static class REC_DATE4 extends JnaStructure {
    public short evnt_type; // event type
    public short year; // year
    public short month; // month
    public short day; // day
    public short hour; // hour
    public short minute; // minute
    public short second; // second
    public short dummy;
  }

  public static class REC_DATE4_data extends JnaStructure {
    public short rec_len; // length
    public short rec_type; // record type
    public REC_DATE4 data = new REC_DATE4();
  }

  public static class REC_IAL4 extends JnaStructure {
    public short alm_grp; // alarm group
    public short alm_no; // alarm number
    public short axis_no; // axis number
    public short year; // year
    public short month; // month
    public short day; // day
    public short hour; // hour
    public short minute; // minute
    public short second; // second
    public short pth_no; // path index
    public short sys_alm; // sys alarm
    public short dsp_flg; // message dsp flag
    public short axis_num; // axis num

    public int[] g_modal = new int[10]; // G code Modal

    public char[] g_dp = new char[10]; // #7:1 Block
    /* #6�`#0 dp*/

    public int[] a_modal = new int[10]; // B,D,E,F,H,M,N,O,S,T code Modal

    public char[] a_dp = new char[10]; // #7:1 Block
    /* 6�`#0 dp*/

    public int[] abs_pos = new int[32]; // Abs pos

    public char[] abs_dp = new char[32]; // Abs dp

    public int[] mcn_pos = new int[32]; // Mcn pos

    public char[] mcn_dp = new char[32]; // Mcn dp
  }

  public static class REC_IAL4_data extends JnaStructure {
    public short rec_len; // length
    public short rec_type; // record type
    public REC_IAL4 data = new REC_IAL4();
  }

  public static class REC_MAL4 extends JnaStructure {
    public short alm_grp; // alarm group
    public short alm_no; // alarm number
    public short axis_no; // axis number
    public short year; // year
    public short month; // month
    public short day; // day
    public short hour; // hour
    public short minute; // minute
    public short second; // second
    public short pth_no; // path index
    public short sys_alm; // sys alarm
    public short dsp_flg; // message dsp flag
    public short axis_num; // axis num

    public String alm_msg = StringHelper.repeatChar(' ', 64); // alarm message

    public int[] g_modal = new int[10]; // G code Modal

    public char[] g_dp = new char[10]; // #7:1 Block
    /* #6�`#0 dp*/

    public int[] a_modal = new int[10]; // B,D,E,F,H,M,N,O,S,T code Modal

    public char[] a_dp = new char[10]; // #7:1 Block
    /* 6�`#0 dp*/

    public int[] abs_pos = new int[64]; // Abs pos

    public char[] abs_dp = new char[64]; // Abs dp

    public int[] mcn_pos = new int[64]; // Mcn pos

    public char[] mcn_dp = new char[64]; // Mcn dp
  }

  public static class REC_MAL4_data extends JnaStructure {
    public short rec_len; // length
    public short rec_type; // record type
    public REC_MAL4 data = new REC_MAL4();
  }

  public static class REC_OPM4 extends JnaStructure {
    public short dsp_flg; // Dysplay flag(ON/OFF)
    public short om_no; // message number
    public short year; // year
    public short month; // month
    public short day; // day
    public short hour; // Hour
    public short minute; // Minute
    public short second; // Second

    public String ope_msg = StringHelper.repeatChar(' ', 256); // Messege
  }

  public static class REC_OPM4_data extends JnaStructure {
    public short rec_len; // length
    public short rec_type; // record type
    public REC_OPM4 data = new REC_OPM4();
  }

  public static class REC_OFS4 extends JnaStructure {
    public short ofs_grp; // Tool offset group
    public short ofs_no; // Tool offset number
    public short hour; // hour
    public short minute; // minute
    public short second; // second
    public short pth_no; // path index
    public int ofs_old; // old data
    public int ofs_new; // new data
    public short old_dp; // old data decimal point
    public short new_dp; // new data decimal point
  }

  public static class REC_OFS4_data extends JnaStructure {
    public short rec_len; // length
    public short rec_type; // record type
    public REC_OFS4 data = new REC_OFS4();
  }

  public static class REC_PRM4 extends JnaStructure {
    public short prm_grp; // paramater group
    public short prm_num; // paramater number
    public short hour; // hour
    public short minute; // minute
    public short second; // second
    public short prm_len; // paramater data length
    public int prm_no; // paramater no
    public int prm_old; // old data
    public int prm_new; // new data
    public short old_dp; // old data decimal point
    public short new_dp; // new data decimal point
  }

  public static class REC_PRM4_data extends JnaStructure {
    public short rec_len; // length
    public short rec_type; // record type
    public REC_PRM4 data = new REC_PRM4();
  }

  public static class REC_WOF4 extends JnaStructure {
    public short ofs_grp; // Work offset group
    public short ofs_no; // Work offset number
    public short hour; // hour
    public short minute; // minute
    public short second; // second
    public short pth_no; // path index
    public short axis_no; // path axis num $
    public short dummy;
    public int ofs_old; // old data
    public int ofs_new; // new data
    public short old_dp; // old data decimal point
    public short new_dp; // new data decimal point
  }

  public static class REC_WOF4_data extends JnaStructure {
    public short rec_len; // length
    public short rec_type; // record type
    public REC_WOF4 data = new REC_WOF4();
  }

  public static class REC_MAC4 extends JnaStructure {
    public short mac_no; // macro val number
    public short hour; // hour
    public short minute; // minute
    public short second; // second
    public short pth_no; // path index
    public int mac_old; // old data
    public int mac_new; // new data
    public short old_dp; // old data decimal point
    public short new_dp; // old data decimal point
  }

  public static class REC_MAC4_data extends JnaStructure {
    public short rec_len; // length
    public short rec_type; // record type
    public REC_MAC4 data = new REC_MAC4();
  }

  public static class ODBOPHIS4_1 extends JnaStructure {
    public REC_MDI4_data rec_mdi1 = new REC_MDI4_data();
    public REC_MDI4_data rec_mdi2 = new REC_MDI4_data();
    public REC_MDI4_data rec_mdi3 = new REC_MDI4_data();
    public REC_MDI4_data rec_mdi4 = new REC_MDI4_data();
    public REC_MDI4_data rec_mdi5 = new REC_MDI4_data();
    public REC_MDI4_data rec_mdi6 = new REC_MDI4_data();
    public REC_MDI4_data rec_mdi7 = new REC_MDI4_data();
    public REC_MDI4_data rec_mdi8 = new REC_MDI4_data();
    public REC_MDI4_data rec_mdi9 = new REC_MDI4_data();
    public REC_MDI4_data rec_mdi10 = new REC_MDI4_data();
  } // In case that the number of data is 10

  public static class ODBOPHIS4_2 extends JnaStructure {
    public REC_SGN4_data rec_sgn1 = new REC_SGN4_data();
    public REC_SGN4_data rec_sgn2 = new REC_SGN4_data();
    public REC_SGN4_data rec_sgn3 = new REC_SGN4_data();
    public REC_SGN4_data rec_sgn4 = new REC_SGN4_data();
    public REC_SGN4_data rec_sgn5 = new REC_SGN4_data();
    public REC_SGN4_data rec_sgn6 = new REC_SGN4_data();
    public REC_SGN4_data rec_sgn7 = new REC_SGN4_data();
    public REC_SGN4_data rec_sgn8 = new REC_SGN4_data();
    public REC_SGN4_data rec_sgn9 = new REC_SGN4_data();
    public REC_SGN4_data rec_sgn10 = new REC_SGN4_data();
  } // In case that the number of data is 10

  public static class ODBOPHIS4_3 extends JnaStructure {
    public REC_ALM4_data rec_alm1 = new REC_ALM4_data();
    public REC_ALM4_data rec_alm2 = new REC_ALM4_data();
    public REC_ALM4_data rec_alm3 = new REC_ALM4_data();
    public REC_ALM4_data rec_alm4 = new REC_ALM4_data();
    public REC_ALM4_data rec_alm5 = new REC_ALM4_data();
    public REC_ALM4_data rec_alm6 = new REC_ALM4_data();
    public REC_ALM4_data rec_alm7 = new REC_ALM4_data();
    public REC_ALM4_data rec_alm8 = new REC_ALM4_data();
    public REC_ALM4_data rec_alm9 = new REC_ALM4_data();
    public REC_ALM4_data rec_alm10 = new REC_ALM4_data();
  } // In case that the number of data is 10

  public static class ODBOPHIS4_4 extends JnaStructure {
    public REC_DATE4_data rec_date1 = new REC_DATE4_data();
    public REC_DATE4_data rec_date2 = new REC_DATE4_data();
    public REC_DATE4_data rec_date3 = new REC_DATE4_data();
    public REC_DATE4_data rec_date4 = new REC_DATE4_data();
    public REC_DATE4_data rec_date5 = new REC_DATE4_data();
    public REC_DATE4_data rec_date6 = new REC_DATE4_data();
    public REC_DATE4_data rec_date7 = new REC_DATE4_data();
    public REC_DATE4_data rec_date8 = new REC_DATE4_data();
    public REC_DATE4_data rec_date9 = new REC_DATE4_data();
    public REC_DATE4_data rec_date10 = new REC_DATE4_data();
  } // In case that the number of data is 10

  public static class ODBOPHIS4_5 extends JnaStructure {
    public REC_IAL4_data rec_ial1 = new REC_IAL4_data();
    public REC_IAL4_data rec_ial2 = new REC_IAL4_data();
    public REC_IAL4_data rec_ial3 = new REC_IAL4_data();
    public REC_IAL4_data rec_ial4 = new REC_IAL4_data();
    public REC_IAL4_data rec_ial5 = new REC_IAL4_data();
    public REC_IAL4_data rec_ial6 = new REC_IAL4_data();
    public REC_IAL4_data rec_ial7 = new REC_IAL4_data();
    public REC_IAL4_data rec_ial8 = new REC_IAL4_data();
    public REC_IAL4_data rec_ial9 = new REC_IAL4_data();
    public REC_IAL4_data rec_ial10 = new REC_IAL4_data();
  } // In case that the number of data is 10

  public static class ODBOPHIS4_6 extends JnaStructure {
    public REC_MAL4_data rec_mal1 = new REC_MAL4_data();
    public REC_MAL4_data rec_mal2 = new REC_MAL4_data();
    public REC_MAL4_data rec_mal3 = new REC_MAL4_data();
    public REC_MAL4_data rec_mal4 = new REC_MAL4_data();
    public REC_MAL4_data rec_mal5 = new REC_MAL4_data();
    public REC_MAL4_data rec_mal6 = new REC_MAL4_data();
    public REC_MAL4_data rec_mal7 = new REC_MAL4_data();
    public REC_MAL4_data rec_mal8 = new REC_MAL4_data();
    public REC_MAL4_data rec_mal9 = new REC_MAL4_data();
    public REC_MAL4_data rec_mal10 = new REC_MAL4_data();
  } // In case that the number of data is 10

  public static class ODBOPHIS4_7 extends JnaStructure {
    public REC_OPM4_data rec_opm1 = new REC_OPM4_data();
    public REC_OPM4_data rec_opm2 = new REC_OPM4_data();
    public REC_OPM4_data rec_opm3 = new REC_OPM4_data();
    public REC_OPM4_data rec_opm4 = new REC_OPM4_data();
    public REC_OPM4_data rec_opm5 = new REC_OPM4_data();
    public REC_OPM4_data rec_opm6 = new REC_OPM4_data();
    public REC_OPM4_data rec_opm7 = new REC_OPM4_data();
    public REC_OPM4_data rec_opm8 = new REC_OPM4_data();
    public REC_OPM4_data rec_opm9 = new REC_OPM4_data();
    public REC_OPM4_data rec_opm10 = new REC_OPM4_data();
  } // In case that the number of data is 10

  public static class ODBOPHIS4_8 extends JnaStructure {
    public REC_OFS4_data rec_ofs1 = new REC_OFS4_data();
    public REC_OFS4_data rec_ofs2 = new REC_OFS4_data();
    public REC_OFS4_data rec_ofs3 = new REC_OFS4_data();
    public REC_OFS4_data rec_ofs4 = new REC_OFS4_data();
    public REC_OFS4_data rec_ofs5 = new REC_OFS4_data();
    public REC_OFS4_data rec_ofs6 = new REC_OFS4_data();
    public REC_OFS4_data rec_ofs7 = new REC_OFS4_data();
    public REC_OFS4_data rec_ofs8 = new REC_OFS4_data();
    public REC_OFS4_data rec_ofs9 = new REC_OFS4_data();
    public REC_OFS4_data rec_ofs10 = new REC_OFS4_data();
  } // In case that the number of data is 10

  public static class ODBOPHIS4_9 extends JnaStructure {
    public REC_PRM4_data rec_prm1 = new REC_PRM4_data();
    public REC_PRM4_data rec_prm2 = new REC_PRM4_data();
    public REC_PRM4_data rec_prm3 = new REC_PRM4_data();
    public REC_PRM4_data rec_prm4 = new REC_PRM4_data();
    public REC_PRM4_data rec_prm5 = new REC_PRM4_data();
    public REC_PRM4_data rec_prm6 = new REC_PRM4_data();
    public REC_PRM4_data rec_prm7 = new REC_PRM4_data();
    public REC_PRM4_data rec_prm8 = new REC_PRM4_data();
    public REC_PRM4_data rec_prm9 = new REC_PRM4_data();
    public REC_PRM4_data rec_prm10 = new REC_PRM4_data();
  } // In case that the number of data is 10

  public static class ODBOPHIS4_10 extends JnaStructure {
    public REC_WOF4_data rec_wof1 = new REC_WOF4_data();
    public REC_WOF4_data rec_wof2 = new REC_WOF4_data();
    public REC_WOF4_data rec_wof3 = new REC_WOF4_data();
    public REC_WOF4_data rec_wof4 = new REC_WOF4_data();
    public REC_WOF4_data rec_wof5 = new REC_WOF4_data();
    public REC_WOF4_data rec_wof6 = new REC_WOF4_data();
    public REC_WOF4_data rec_wof7 = new REC_WOF4_data();
    public REC_WOF4_data rec_wof8 = new REC_WOF4_data();
    public REC_WOF4_data rec_wof9 = new REC_WOF4_data();
    public REC_WOF4_data rec_wof10 = new REC_WOF4_data();
  } // In case that the number of data is 10

  public static class ODBOPHIS4_11 extends JnaStructure {
    public REC_MAC4_data rec_mac1 = new REC_MAC4_data();
    public REC_MAC4_data rec_mac2 = new REC_MAC4_data();
    public REC_MAC4_data rec_mac3 = new REC_MAC4_data();
    public REC_MAC4_data rec_mac4 = new REC_MAC4_data();
    public REC_MAC4_data rec_mac5 = new REC_MAC4_data();
    public REC_MAC4_data rec_mac6 = new REC_MAC4_data();
    public REC_MAC4_data rec_mac7 = new REC_MAC4_data();
    public REC_MAC4_data rec_mac8 = new REC_MAC4_data();
    public REC_MAC4_data rec_mac9 = new REC_MAC4_data();
    public REC_MAC4_data rec_mac10 = new REC_MAC4_data();
  } // In case that the number of data is 10

  /* cnc_rdalmhistry:read alarm history data */
  public static class ALM_HIS_data extends JnaStructure {
    public short dummy;
    public short alm_grp; // alarm group
    public short alm_no; // alarm number

    public byte axis_no; // axis number

    public byte year; // year

    public byte month; // month

    public byte day; // day

    public byte hour; // hour

    public byte minute; // minute

    public byte second; // second

    public byte dummy2;
    public short len_msg; // alarm message length

    public String alm_msg = StringHelper.repeatChar(' ', 32); // alarm message
  }

  public static class ALM_HIS1 extends JnaStructure {
    public ALM_HIS_data data1 = new ALM_HIS_data();
    public ALM_HIS_data data2 = new ALM_HIS_data();
    public ALM_HIS_data data3 = new ALM_HIS_data();
    public ALM_HIS_data data4 = new ALM_HIS_data();
    public ALM_HIS_data data5 = new ALM_HIS_data();
    public ALM_HIS_data data6 = new ALM_HIS_data();
    public ALM_HIS_data data7 = new ALM_HIS_data();
    public ALM_HIS_data data8 = new ALM_HIS_data();
    public ALM_HIS_data data9 = new ALM_HIS_data();
    public ALM_HIS_data data10 = new ALM_HIS_data();
  } // In case that the number of data is 10

  public static class ODBAHIS extends JnaStructure {

    public short s_no; // start number
    public short type; // dummy

    public short e_no; // end number
    public ALM_HIS1 alm_his = new ALM_HIS1();
  }

  /* cnc_rdalmhistry2:read alarm history data */
  public static class ALM_HIS2_data extends JnaStructure {
    public short alm_grp; // alarm group
    public short alm_no; // alarm number
    public short axis_no; // axis number
    public short year; // year
    public short month; // month
    public short day; // day
    public short hour; // hour
    public short minute; // minute
    public short second; // second
    public short len_msg; // alarm message length

    public String alm_msg = StringHelper.repeatChar(' ', 32); // alarm message
  }

  public static class ALM_HIS2 extends JnaStructure {
    public ALM_HIS2_data data1 = new ALM_HIS2_data();
    public ALM_HIS2_data data2 = new ALM_HIS2_data();
    public ALM_HIS2_data data3 = new ALM_HIS2_data();
    public ALM_HIS2_data data4 = new ALM_HIS2_data();
    public ALM_HIS2_data data5 = new ALM_HIS2_data();
    public ALM_HIS2_data data6 = new ALM_HIS2_data();
    public ALM_HIS2_data data7 = new ALM_HIS2_data();
    public ALM_HIS2_data data8 = new ALM_HIS2_data();
    public ALM_HIS2_data data9 = new ALM_HIS2_data();
    public ALM_HIS2_data data10 = new ALM_HIS2_data();
  } // In case that the number of data is 10

  public static class ODBAHIS2 extends JnaStructure {

    public short s_no; // start number

    public short e_no; // end number
    public ALM_HIS2 alm_his = new ALM_HIS2();
  }

  /* cnc_rdalmhistry3:read alarm history data */
  public static class ALM_HIS3_data extends JnaStructure {
    public short alm_grp; // alarm group
    public short alm_no; // alarm number
    public short axis_no; // axis number
    public short year; // year
    public short month; // month
    public short day; // day
    public short hour; // hour
    public short minute; // minute
    public short second; // second
    public short len_msg; // alarm message length
    public short pth_no; // path index
    public short dummy;

    public String alm_msg = StringHelper.repeatChar(' ', 32); // alarm message
  }

  public static class ALM_HIS3 extends JnaStructure {
    public ALM_HIS3_data data1 = new ALM_HIS3_data();
    public ALM_HIS3_data data2 = new ALM_HIS3_data();
    public ALM_HIS3_data data3 = new ALM_HIS3_data();
    public ALM_HIS3_data data4 = new ALM_HIS3_data();
    public ALM_HIS3_data data5 = new ALM_HIS3_data();
    public ALM_HIS3_data data6 = new ALM_HIS3_data();
    public ALM_HIS3_data data7 = new ALM_HIS3_data();
    public ALM_HIS3_data data8 = new ALM_HIS3_data();
    public ALM_HIS3_data data9 = new ALM_HIS3_data();
    public ALM_HIS3_data data10 = new ALM_HIS3_data();
  } // In case that the number of data is 10

  public static class ODBAHIS3 extends JnaStructure {

    public short s_no; // start number

    public short e_no; // end number
    public ALM_HIS3 alm_his = new ALM_HIS3();
  }

  /* cnc_rdalmhistry5:read alarm history data */
  public static class ALM_HIS5_data extends JnaStructure {
    public short alm_grp; // alarm group
    public short alm_no; // alarm number
    public short axis_no; // axis number
    public short year; // year
    public short month; // month
    public short day; // day
    public short hour; // hour
    public short minute; // minute
    public short second; // second
    public short len_msg; // alarm message length
    public short pth_no; // path index
    public short dummy; // dummy
    public short dsp_flg; // Flag for displaying
    public short axis_num; // Total axis number

    public String alm_msg = StringHelper.repeatChar(' ', 64); // alarm message

    public int[] g_modal = new int[10]; // G code Modal

    public byte[] g_dp = new byte[10]; // #7:1 Block  #6�`#0 dp

    public int[] a_modal = new int[10]; // B,D,E,F,H,M,N,O,S,T code Modal

    public byte[] a_dp = new byte[10]; // #7:1 Block  #6�`#0 dp

    public int[] abs_pos = new int[32]; // Abs pos

    public byte[] abs_dp = new byte[32]; // Abs dp

    public int[] mcn_pos = new int[32]; // Mcn pos

    public byte[] mcn_dp = new byte[32]; // Mcn dp
  }

  public static class ALM_HIS5 extends JnaStructure {
    public ALM_HIS5_data data1 = new ALM_HIS5_data();
    public ALM_HIS5_data data2 = new ALM_HIS5_data();
    public ALM_HIS5_data data3 = new ALM_HIS5_data();
    public ALM_HIS5_data data4 = new ALM_HIS5_data();
    public ALM_HIS5_data data5 = new ALM_HIS5_data();
    public ALM_HIS5_data data6 = new ALM_HIS5_data();
    public ALM_HIS5_data data7 = new ALM_HIS5_data();
    public ALM_HIS5_data data8 = new ALM_HIS5_data();
    public ALM_HIS5_data data9 = new ALM_HIS5_data();
    public ALM_HIS5_data data10 = new ALM_HIS5_data();
  } // In case that the number of data is 10

  public static class ODBAHIS5 extends JnaStructure {

    public short s_no; // start number

    public short e_no; // end number
    public ALM_HIS5 alm_his = new ALM_HIS5();
  }

  /* cnc_rdomhistry2:read operater message history data */
  public static class ODBOMHIS2_data extends JnaStructure {
    public short dsp_flg; // Dysplay flag(ON/OFF)
    public short om_no; // operater message number
    public short year; // year
    public short month; // month
    public short day; // day
    public short hour; // Hour
    public short minute; // Minute
    public short second; // Second

    public String alm_msg = StringHelper.repeatChar(' ', 256); // alarm message
  }

  public static class OPM_HIS extends JnaStructure {
    public ODBOMHIS2_data data1 = new ODBOMHIS2_data();
    public ODBOMHIS2_data data2 = new ODBOMHIS2_data();
    public ODBOMHIS2_data data3 = new ODBOMHIS2_data();
    public ODBOMHIS2_data data4 = new ODBOMHIS2_data();
    public ODBOMHIS2_data data5 = new ODBOMHIS2_data();
    public ODBOMHIS2_data data6 = new ODBOMHIS2_data();
    public ODBOMHIS2_data data7 = new ODBOMHIS2_data();
    public ODBOMHIS2_data data8 = new ODBOMHIS2_data();
    public ODBOMHIS2_data data9 = new ODBOMHIS2_data();
    public ODBOMHIS2_data data10 = new ODBOMHIS2_data();
  } // In case that the number of data is 10

  public static class ODBOMHIS2 extends JnaStructure {

    public short s_no; // start number

    public short e_no; // end number
    public OPM_HIS opm_his = new OPM_HIS();
  }

  /* cnc_rdhissgnl:read signals related operation history */
  /* cnc_wrhissgnl:write signals related operation history */
  public static class IODBSIG_data extends JnaStructure {
    public short ent_no; // entry number
    public short sig_no; // signal number

    public byte sig_name; // signal name

    public byte mask_pat; // signal mask pattern
  }

  public static class IODBSIG1 extends JnaStructure {
    public IODBSIG_data data1 = new IODBSIG_data();
    public IODBSIG_data data2 = new IODBSIG_data();
    public IODBSIG_data data3 = new IODBSIG_data();
    public IODBSIG_data data4 = new IODBSIG_data();
    public IODBSIG_data data5 = new IODBSIG_data();
    public IODBSIG_data data6 = new IODBSIG_data();
    public IODBSIG_data data7 = new IODBSIG_data();
    public IODBSIG_data data8 = new IODBSIG_data();
    public IODBSIG_data data9 = new IODBSIG_data();
    public IODBSIG_data data10 = new IODBSIG_data();
    public IODBSIG_data data11 = new IODBSIG_data();
    public IODBSIG_data data12 = new IODBSIG_data();
    public IODBSIG_data data13 = new IODBSIG_data();
    public IODBSIG_data data14 = new IODBSIG_data();
    public IODBSIG_data data15 = new IODBSIG_data();
    public IODBSIG_data data16 = new IODBSIG_data();
    public IODBSIG_data data17 = new IODBSIG_data();
    public IODBSIG_data data18 = new IODBSIG_data();
    public IODBSIG_data data19 = new IODBSIG_data();
    public IODBSIG_data data20 = new IODBSIG_data();
  }

  public static class IODBSIG extends JnaStructure {
    public short datano; // dummy
    public short type; // dummy
    public IODBSIG1 data = new IODBSIG1();
  }

  /* cnc_rdhissgnl2:read signals related operation history 2 */
  /* cnc_wrhissgnl2:write signals related operation history 2 */
  public static class _IODBSIG2_data extends JnaStructure {
    public short ent_no; // entry number
    public short sig_no; // signal number

    public byte sig_name; // signal name

    public byte mask_pat; // signal mask pattern
  }

  public static class IODBSIG2_data extends JnaStructure {
    public _IODBSIG2_data data1 = new _IODBSIG2_data();
    public _IODBSIG2_data data2 = new _IODBSIG2_data();
    public _IODBSIG2_data data3 = new _IODBSIG2_data();
    public _IODBSIG2_data data4 = new _IODBSIG2_data();
    public _IODBSIG2_data data5 = new _IODBSIG2_data();
    public _IODBSIG2_data data6 = new _IODBSIG2_data();
    public _IODBSIG2_data data7 = new _IODBSIG2_data();
    public _IODBSIG2_data data8 = new _IODBSIG2_data();
    public _IODBSIG2_data data9 = new _IODBSIG2_data();
    public _IODBSIG2_data data10 = new _IODBSIG2_data();
    public _IODBSIG2_data data11 = new _IODBSIG2_data();
    public _IODBSIG2_data data12 = new _IODBSIG2_data();
    public _IODBSIG2_data data13 = new _IODBSIG2_data();
    public _IODBSIG2_data data14 = new _IODBSIG2_data();
    public _IODBSIG2_data data15 = new _IODBSIG2_data();
    public _IODBSIG2_data data16 = new _IODBSIG2_data();
    public _IODBSIG2_data data17 = new _IODBSIG2_data();
    public _IODBSIG2_data data18 = new _IODBSIG2_data();
    public _IODBSIG2_data data19 = new _IODBSIG2_data();
    public _IODBSIG2_data data20 = new _IODBSIG2_data();
    public _IODBSIG2_data data31 = new _IODBSIG2_data();
    public _IODBSIG2_data data32 = new _IODBSIG2_data();
    public _IODBSIG2_data data33 = new _IODBSIG2_data();
    public _IODBSIG2_data data34 = new _IODBSIG2_data();
    public _IODBSIG2_data data35 = new _IODBSIG2_data();
    public _IODBSIG2_data data36 = new _IODBSIG2_data();
    public _IODBSIG2_data data37 = new _IODBSIG2_data();
    public _IODBSIG2_data data38 = new _IODBSIG2_data();
    public _IODBSIG2_data data39 = new _IODBSIG2_data();
    public _IODBSIG2_data data40 = new _IODBSIG2_data();
    public _IODBSIG2_data data41 = new _IODBSIG2_data();
    public _IODBSIG2_data data42 = new _IODBSIG2_data();
    public _IODBSIG2_data data43 = new _IODBSIG2_data();
    public _IODBSIG2_data data44 = new _IODBSIG2_data();
    public _IODBSIG2_data data45 = new _IODBSIG2_data();
  }

  public static class IODBSIG2 extends JnaStructure {
    public short datano; // dummy
    public short type; // dummy
    public IODBSIG2_data data = new IODBSIG2_data();
  }

  /* cnc_rdhissgnl3:read signals related operation history */
  /* cnc_wrhissgnl3:write signals related operation history */
  public static class _IODBSIG3_data extends JnaStructure {
    public short ent_no; // entry number
    public short pmc_no; // pmc number
    public short sig_no; // signal number

    public byte sig_name; // signal name

    public byte mask_pat; // signal mask pattern
  }

  public static class IODBSIG3_data extends JnaStructure {
    public _IODBSIG3_data data1 = new _IODBSIG3_data();
    public _IODBSIG3_data data2 = new _IODBSIG3_data();
    public _IODBSIG3_data data3 = new _IODBSIG3_data();
    public _IODBSIG3_data data4 = new _IODBSIG3_data();
    public _IODBSIG3_data data5 = new _IODBSIG3_data();
    public _IODBSIG3_data data6 = new _IODBSIG3_data();
    public _IODBSIG3_data data7 = new _IODBSIG3_data();
    public _IODBSIG3_data data8 = new _IODBSIG3_data();
    public _IODBSIG3_data data9 = new _IODBSIG3_data();
    public _IODBSIG3_data data10 = new _IODBSIG3_data();
    public _IODBSIG3_data data11 = new _IODBSIG3_data();
    public _IODBSIG3_data data12 = new _IODBSIG3_data();
    public _IODBSIG3_data data13 = new _IODBSIG3_data();
    public _IODBSIG3_data data14 = new _IODBSIG3_data();
    public _IODBSIG3_data data15 = new _IODBSIG3_data();
    public _IODBSIG3_data data16 = new _IODBSIG3_data();
    public _IODBSIG3_data data17 = new _IODBSIG3_data();
    public _IODBSIG3_data data18 = new _IODBSIG3_data();
    public _IODBSIG3_data data19 = new _IODBSIG3_data();
    public _IODBSIG3_data data20 = new _IODBSIG3_data();
    public _IODBSIG3_data data21 = new _IODBSIG3_data();
    public _IODBSIG3_data data22 = new _IODBSIG3_data();
    public _IODBSIG3_data data23 = new _IODBSIG3_data();
    public _IODBSIG3_data data24 = new _IODBSIG3_data();
    public _IODBSIG3_data data25 = new _IODBSIG3_data();
    public _IODBSIG3_data data26 = new _IODBSIG3_data();
    public _IODBSIG3_data data27 = new _IODBSIG3_data();
    public _IODBSIG3_data data28 = new _IODBSIG3_data();
    public _IODBSIG3_data data29 = new _IODBSIG3_data();
    public _IODBSIG3_data data30 = new _IODBSIG3_data();
    public _IODBSIG3_data data31 = new _IODBSIG3_data();
    public _IODBSIG3_data data32 = new _IODBSIG3_data();
    public _IODBSIG3_data data33 = new _IODBSIG3_data();
    public _IODBSIG3_data data34 = new _IODBSIG3_data();
    public _IODBSIG3_data data35 = new _IODBSIG3_data();
    public _IODBSIG3_data data36 = new _IODBSIG3_data();
    public _IODBSIG3_data data37 = new _IODBSIG3_data();
    public _IODBSIG3_data data38 = new _IODBSIG3_data();
    public _IODBSIG3_data data39 = new _IODBSIG3_data();
    public _IODBSIG3_data data40 = new _IODBSIG3_data();
    public _IODBSIG3_data data41 = new _IODBSIG3_data();
    public _IODBSIG3_data data42 = new _IODBSIG3_data();
    public _IODBSIG3_data data43 = new _IODBSIG3_data();
    public _IODBSIG3_data data44 = new _IODBSIG3_data();
    public _IODBSIG3_data data45 = new _IODBSIG3_data();
    public _IODBSIG3_data data46 = new _IODBSIG3_data();
    public _IODBSIG3_data data47 = new _IODBSIG3_data();
    public _IODBSIG3_data data48 = new _IODBSIG3_data();
    public _IODBSIG3_data data49 = new _IODBSIG3_data();
    public _IODBSIG3_data data50 = new _IODBSIG3_data();
    public _IODBSIG3_data data51 = new _IODBSIG3_data();
    public _IODBSIG3_data data52 = new _IODBSIG3_data();
    public _IODBSIG3_data data53 = new _IODBSIG3_data();
    public _IODBSIG3_data data54 = new _IODBSIG3_data();
    public _IODBSIG3_data data55 = new _IODBSIG3_data();
    public _IODBSIG3_data data56 = new _IODBSIG3_data();
    public _IODBSIG3_data data57 = new _IODBSIG3_data();
    public _IODBSIG3_data data58 = new _IODBSIG3_data();
    public _IODBSIG3_data data59 = new _IODBSIG3_data();
    public _IODBSIG3_data data60 = new _IODBSIG3_data();
  }

  public static class IODBSIG3 extends JnaStructure {
    public short datano; // dummy
    public short type; // dummy
    public IODBSIG3_data data = new IODBSIG3_data();
  }

  /*-------------*/
  /* CNC: Others */
  /*-------------*/
  /* cnc_sysinfo:read CNC system information */
  public static class ODBSYS extends JnaStructure {
    public short addinfo;
    public short max_axis;

    public char[] cnc_type = new char[2];

    public char[] mt_type = new char[2];

    public char[] series = new char[4];

    public char[] version = new char[4];

    public char[] axes = new char[2];
  }

  //#if FS15D
  /* cnc_statinfo:read CNC status information */
  public static class ODBST extends JnaStructure {
    public short dummy;     /* dummy */
    public short tmmode;    /* T/M mode */
    public short aut;       /* selected automatic mode */
    public short run;       /* running status */
    public short motion;    /* axis, dwell status */
    public short mstb;      /* m, s, t, b status */
    public short emergency; /* emergency stop status */
    public short alarm;     /* alarm status */
    public short edit;      /* editting status */
  }

  /* cnc_alarm:read alarm status */
  public static class ODBALM extends JnaStructure {

    public short[] dummy = {0, 0};

    public short data = 0;
  }

  /* cnc_rdalminfo:read alarm information */
//#if M_AXIS2
  public static class ALMINFO1_data extends JnaStructure {
    public int axis;
    public short alm_no;
  }

  public static class ALMINFO2_data extends JnaStructure {
    public int axis = 0;
    public short alm_no = 0;
    public short msg_len = 0;

    public String alm_msg = StringHelper.repeatChar(' ', 32);
  }


  //#endif
  public static class ALMINFO_1 extends JnaStructure {
    public ALMINFO1_data msg1 = new ALMINFO1_data();
    public ALMINFO1_data msg2 = new ALMINFO1_data();
    public ALMINFO1_data msg3 = new ALMINFO1_data();
    public ALMINFO1_data msg4 = new ALMINFO1_data();
    public ALMINFO1_data msg5 = new ALMINFO1_data();
    public short data_end;
  } // In case that the number of alarm is 5

  public static class ALMINFO_2 extends JnaStructure {
    public ALMINFO2_data msg1 = new ALMINFO2_data();
    public ALMINFO2_data msg2 = new ALMINFO2_data();
    public ALMINFO2_data msg3 = new ALMINFO2_data();
    public ALMINFO2_data msg4 = new ALMINFO2_data();
    public ALMINFO2_data msg5 = new ALMINFO2_data();
    public short dataend = 0;
  } // In case that the number of alarm is 5

  /* cnc_rdalmmsg:read alarm messages */
  public static class ODBALMMSG_data extends JnaStructure {
    public int alm_no;
    public short type;
    public short axis;
    public short dummy;
    public short msg_len;

    public String alm_msg = StringHelper.repeatChar(' ', 32);
  }

  public static class ODBALMMSG extends JnaStructure {
    public ODBALMMSG_data msg1 = new ODBALMMSG_data();
    public ODBALMMSG_data msg2 = new ODBALMMSG_data();
    public ODBALMMSG_data msg3 = new ODBALMMSG_data();
    public ODBALMMSG_data msg4 = new ODBALMMSG_data();
    public ODBALMMSG_data msg5 = new ODBALMMSG_data();
    public ODBALMMSG_data msg6 = new ODBALMMSG_data();
    public ODBALMMSG_data msg7 = new ODBALMMSG_data();
    public ODBALMMSG_data msg8 = new ODBALMMSG_data();
    public ODBALMMSG_data msg9 = new ODBALMMSG_data();
    public ODBALMMSG_data msg10 = new ODBALMMSG_data();
  } // In case that the number of alarm is 10

  public static class ODBALMMSG2_data extends JnaStructure {
    public int alm_no;
    public short type;
    public short axis;
    public short dummy;
    public short msg_len;

    public String alm_msg = StringHelper.repeatChar(' ', 64);
  }

  public static class ODBALMMSG2 extends JnaStructure {
    public ODBALMMSG2_data msg1 = new ODBALMMSG2_data();
    public ODBALMMSG2_data msg2 = new ODBALMMSG2_data();
    public ODBALMMSG2_data msg3 = new ODBALMMSG2_data();
    public ODBALMMSG2_data msg4 = new ODBALMMSG2_data();
    public ODBALMMSG2_data msg5 = new ODBALMMSG2_data();
    public ODBALMMSG2_data msg6 = new ODBALMMSG2_data();
    public ODBALMMSG2_data msg7 = new ODBALMMSG2_data();
    public ODBALMMSG2_data msg8 = new ODBALMMSG2_data();
    public ODBALMMSG2_data msg9 = new ODBALMMSG2_data();
    public ODBALMMSG2_data msg10 = new ODBALMMSG2_data();
  } // In case that the number of alarm is 10

  /* cnc_modal:read modal data */
  public static class MODAL_AUX_data extends JnaStructure {
    public int aux_data;

    public byte flag1;

    public byte flag2;
  }

  public static class MODAL_RAUX1_data extends JnaStructure {
    public MODAL_AUX_data data1 = new MODAL_AUX_data();
    public MODAL_AUX_data data2 = new MODAL_AUX_data();
    public MODAL_AUX_data data3 = new MODAL_AUX_data();
    public MODAL_AUX_data data4 = new MODAL_AUX_data();
    public MODAL_AUX_data data5 = new MODAL_AUX_data();
    public MODAL_AUX_data data6 = new MODAL_AUX_data();
    public MODAL_AUX_data data7 = new MODAL_AUX_data();
    public MODAL_AUX_data data8 = new MODAL_AUX_data();
    public MODAL_AUX_data data9 = new MODAL_AUX_data();
    public MODAL_AUX_data data10 = new MODAL_AUX_data();
    public MODAL_AUX_data data11 = new MODAL_AUX_data();
    public MODAL_AUX_data data12 = new MODAL_AUX_data();
    public MODAL_AUX_data data13 = new MODAL_AUX_data();
    public MODAL_AUX_data data14 = new MODAL_AUX_data();
    public MODAL_AUX_data data15 = new MODAL_AUX_data();
    public MODAL_AUX_data data16 = new MODAL_AUX_data();
    public MODAL_AUX_data data17 = new MODAL_AUX_data();
    public MODAL_AUX_data data18 = new MODAL_AUX_data();
    public MODAL_AUX_data data19 = new MODAL_AUX_data();
    public MODAL_AUX_data data20 = new MODAL_AUX_data();
    public MODAL_AUX_data data21 = new MODAL_AUX_data();
    public MODAL_AUX_data data22 = new MODAL_AUX_data();
    public MODAL_AUX_data data23 = new MODAL_AUX_data();
    public MODAL_AUX_data data24 = new MODAL_AUX_data();
    public MODAL_AUX_data data25 = new MODAL_AUX_data();
    public MODAL_AUX_data data26 = new MODAL_AUX_data();
    public MODAL_AUX_data data27 = new MODAL_AUX_data();
  }

  //#if M_AXIS2
  public static class MODAL_RAUX2_data extends JnaStructure {
    public MODAL_AUX_data data1 = new MODAL_AUX_data();
    public MODAL_AUX_data data2 = new MODAL_AUX_data();
    public MODAL_AUX_data data3 = new MODAL_AUX_data();
    public MODAL_AUX_data data4 = new MODAL_AUX_data();
    public MODAL_AUX_data data5 = new MODAL_AUX_data();
    public MODAL_AUX_data data6 = new MODAL_AUX_data();
    public MODAL_AUX_data data7 = new MODAL_AUX_data();
    public MODAL_AUX_data data8 = new MODAL_AUX_data();
    public MODAL_AUX_data data9 = new MODAL_AUX_data();
    public MODAL_AUX_data data10 = new MODAL_AUX_data();
    public MODAL_AUX_data data11 = new MODAL_AUX_data();
    public MODAL_AUX_data data12 = new MODAL_AUX_data();
    public MODAL_AUX_data data13 = new MODAL_AUX_data();
    public MODAL_AUX_data data14 = new MODAL_AUX_data();
    public MODAL_AUX_data data15 = new MODAL_AUX_data();
    public MODAL_AUX_data data16 = new MODAL_AUX_data();
    public MODAL_AUX_data data17 = new MODAL_AUX_data();
    public MODAL_AUX_data data18 = new MODAL_AUX_data();
    public MODAL_AUX_data data19 = new MODAL_AUX_data();
    public MODAL_AUX_data data20 = new MODAL_AUX_data();
    public MODAL_AUX_data data21 = new MODAL_AUX_data();
    public MODAL_AUX_data data22 = new MODAL_AUX_data();
    public MODAL_AUX_data data23 = new MODAL_AUX_data();
    public MODAL_AUX_data data24 = new MODAL_AUX_data();
  }

  //#elif FS15D
 /* public static class MODAL_RAUX2_data
  {
    public MODAL_AUX_data data1 = new MODAL_AUX_data();
    public MODAL_AUX_data data2 = new MODAL_AUX_data();
    public MODAL_AUX_data data3 = new MODAL_AUX_data();
    public MODAL_AUX_data data4 = new MODAL_AUX_data();
    public MODAL_AUX_data data5 = new MODAL_AUX_data();
    public MODAL_AUX_data data6 = new MODAL_AUX_data();
    public MODAL_AUX_data data7 = new MODAL_AUX_data();
    public MODAL_AUX_data data8 = new MODAL_AUX_data();
    public MODAL_AUX_data data9 = new MODAL_AUX_data();
    public MODAL_AUX_data data10 = new MODAL_AUX_data();
  }*/
  //#else
  /*public static class MODAL_RAUX2_data
  {
    public MODAL_AUX_data data1 = new MODAL_AUX_data();
    public MODAL_AUX_data data2 = new MODAL_AUX_data();
    public MODAL_AUX_data data3 = new MODAL_AUX_data();
    public MODAL_AUX_data data4 = new MODAL_AUX_data();
    public MODAL_AUX_data data5 = new MODAL_AUX_data();
    public MODAL_AUX_data data6 = new MODAL_AUX_data();
    public MODAL_AUX_data data7 = new MODAL_AUX_data();
    public MODAL_AUX_data data8 = new MODAL_AUX_data();
  }*/
//#endif
  public static class ODBMDL_1 extends JnaStructure {

    public short datano;

    public short type;

    public byte g_data;
  }

  public static class ODBMDL_2 extends JnaStructure {

    public short datano;

    public short type;

    public byte[] g_1shot = new byte[4];

    public byte[] g_rdata = new byte[35];
  }

  public static class ODBMDL_3 extends JnaStructure {
    public short datano;
    public short type;
    public MODAL_AUX_data aux = new MODAL_AUX_data();
  }

  public static class ODBMDL_4 extends JnaStructure {
    public short datano;
    public short type;
    public MODAL_RAUX1_data raux1 = new MODAL_RAUX1_data();
  }

  public static class ODBMDL_5 extends JnaStructure {
    public short datano;
    public short type;
    public MODAL_RAUX2_data raux2 = new MODAL_RAUX2_data();
  }

  /* cnc_rdgcode: read G code */
  public static class ODBGCD_data extends JnaStructure {
    public short group;
    public short flag;

    public String code = StringHelper.repeatChar(' ', 8);
  }

  public static class ODBGCD extends JnaStructure {
    public ODBGCD_data gcd0 = new ODBGCD_data();
    public ODBGCD_data gcd1 = new ODBGCD_data();
    public ODBGCD_data gcd2 = new ODBGCD_data();
    public ODBGCD_data gcd3 = new ODBGCD_data();
    public ODBGCD_data gcd4 = new ODBGCD_data();
    public ODBGCD_data gcd5 = new ODBGCD_data();
    public ODBGCD_data gcd6 = new ODBGCD_data();
    public ODBGCD_data gcd7 = new ODBGCD_data();
    public ODBGCD_data gcd8 = new ODBGCD_data();
    public ODBGCD_data gcd9 = new ODBGCD_data();
    public ODBGCD_data gcd10 = new ODBGCD_data();
    public ODBGCD_data gcd11 = new ODBGCD_data();
    public ODBGCD_data gcd12 = new ODBGCD_data();
    public ODBGCD_data gcd13 = new ODBGCD_data();
    public ODBGCD_data gcd14 = new ODBGCD_data();
    public ODBGCD_data gcd15 = new ODBGCD_data();
    public ODBGCD_data gcd16 = new ODBGCD_data();
    public ODBGCD_data gcd17 = new ODBGCD_data();
    public ODBGCD_data gcd18 = new ODBGCD_data();
    public ODBGCD_data gcd19 = new ODBGCD_data();
    public ODBGCD_data gcd20 = new ODBGCD_data();
    public ODBGCD_data gcd21 = new ODBGCD_data();
    public ODBGCD_data gcd22 = new ODBGCD_data();
    public ODBGCD_data gcd23 = new ODBGCD_data();
    public ODBGCD_data gcd24 = new ODBGCD_data();
    public ODBGCD_data gcd25 = new ODBGCD_data();
    public ODBGCD_data gcd26 = new ODBGCD_data();
    public ODBGCD_data gcd27 = new ODBGCD_data();
  }

  /* cnc_rdcommand: read command value */
  public static class ODBCMD_data extends JnaStructure {

    public byte adrs;

    public byte num;
    public short flag;
    public int cmd_val;
    public int dec_val;
  }

  public static class ODBCMD extends JnaStructure {
    public ODBCMD_data cmd0 = new ODBCMD_data();
    public ODBCMD_data cmd1 = new ODBCMD_data();
    public ODBCMD_data cmd2 = new ODBCMD_data();
    public ODBCMD_data cmd3 = new ODBCMD_data();
    public ODBCMD_data cmd4 = new ODBCMD_data();
    public ODBCMD_data cmd5 = new ODBCMD_data();
    public ODBCMD_data cmd6 = new ODBCMD_data();
    public ODBCMD_data cmd7 = new ODBCMD_data();
    public ODBCMD_data cmd8 = new ODBCMD_data();
    public ODBCMD_data cmd9 = new ODBCMD_data();
    public ODBCMD_data cmd10 = new ODBCMD_data();
    public ODBCMD_data cmd11 = new ODBCMD_data();
    public ODBCMD_data cmd12 = new ODBCMD_data();
    public ODBCMD_data cmd13 = new ODBCMD_data();
    public ODBCMD_data cmd14 = new ODBCMD_data();
    public ODBCMD_data cmd15 = new ODBCMD_data();
    public ODBCMD_data cmd16 = new ODBCMD_data();
    public ODBCMD_data cmd17 = new ODBCMD_data();
    public ODBCMD_data cmd18 = new ODBCMD_data();
    public ODBCMD_data cmd19 = new ODBCMD_data();
    public ODBCMD_data cmd20 = new ODBCMD_data();
    public ODBCMD_data cmd21 = new ODBCMD_data();
    public ODBCMD_data cmd22 = new ODBCMD_data();
    public ODBCMD_data cmd23 = new ODBCMD_data();
    public ODBCMD_data cmd24 = new ODBCMD_data();
    public ODBCMD_data cmd25 = new ODBCMD_data();
    public ODBCMD_data cmd26 = new ODBCMD_data();
    public ODBCMD_data cmd27 = new ODBCMD_data();
    public ODBCMD_data cmd28 = new ODBCMD_data();
    public ODBCMD_data cmd29 = new ODBCMD_data();
  }

  /* cnc_diagnoss:read diagnosis data */
  /* cnc_diagnosr:read diagnosis data(area specified) */
  public static class REALDGN extends JnaStructure {
    public int dgn_val; // data of real diagnoss
    public int dec_val; // decimal point of real diagnoss
  }

  //#if M_AXIS2
  public static class REALDGNS extends JnaStructure {
    public REALDGN rdata1 = new REALDGN();
    public REALDGN rdata2 = new REALDGN();
    public REALDGN rdata3 = new REALDGN();
    public REALDGN rdata4 = new REALDGN();
    public REALDGN rdata5 = new REALDGN();
    public REALDGN rdata6 = new REALDGN();
    public REALDGN rdata7 = new REALDGN();
    public REALDGN rdata8 = new REALDGN();
    public REALDGN rdata9 = new REALDGN();
    public REALDGN rdata10 = new REALDGN();
    public REALDGN rdata11 = new REALDGN();
    public REALDGN rdata12 = new REALDGN();
    public REALDGN rdata13 = new REALDGN();
    public REALDGN rdata14 = new REALDGN();
    public REALDGN rdata15 = new REALDGN();
    public REALDGN rdata16 = new REALDGN();
    public REALDGN rdata17 = new REALDGN();
    public REALDGN rdata18 = new REALDGN();
    public REALDGN rdata19 = new REALDGN();
    public REALDGN rdata20 = new REALDGN();
    public REALDGN rdata21 = new REALDGN();
    public REALDGN rdata22 = new REALDGN();
    public REALDGN rdata23 = new REALDGN();
    public REALDGN rdata24 = new REALDGN();
  } // In case that the number of alarm is 24

  //#elif FS15D
/*  public static class REALDGNS
  {
    public REALDGN rdata1 = new REALDGN();
    public REALDGN rdata2 = new REALDGN();
    public REALDGN rdata3 = new REALDGN();
    public REALDGN rdata4 = new REALDGN();
    public REALDGN rdata5 = new REALDGN();
    public REALDGN rdata6 = new REALDGN();
    public REALDGN rdata7 = new REALDGN();
    public REALDGN rdata8 = new REALDGN();
    public REALDGN rdata9 = new REALDGN();
    public REALDGN rdata10 = new REALDGN();
  } */// In case that the number of alarm is 10
  //#else
  /*public static class REALDGNS
  {
    public REALDGN rdata1 = new REALDGN();
    public REALDGN rdata2 = new REALDGN();
    public REALDGN rdata3 = new REALDGN();
    public REALDGN rdata4 = new REALDGN();
    public REALDGN rdata5 = new REALDGN();
    public REALDGN rdata6 = new REALDGN();
    public REALDGN rdata7 = new REALDGN();
    public REALDGN rdata8 = new REALDGN();
  } */// In case that the number of alarm is 8
//#endif
  public static class ODBDGN_1 extends JnaStructure {

    public short datano; // data number

    public short type; // axis number

    public byte cdata; // parameter / setting data

    public short idata;

    public int ldata;
  }

  public static class ODBDGN_2 extends JnaStructure {
    public short datano; // data number
    public short type; // axis number
    public REALDGN rdata = new REALDGN();
  }

  public static class ODBDGN_3 extends JnaStructure {

    public short datano; // data number

    public short type; // axis number

    public byte[] cdatas = new byte[MAX_AXIS];

    public short[] idatas = new short[MAX_AXIS];

    public int[] ldatas = new int[MAX_AXIS];
  }

  public static class ODBDGN_4 extends JnaStructure {
    public short datano; // data number
    public short type; // axis number
    public REALDGNS rdatas = new REALDGNS();
  }

  public static class ODBDGN_A extends JnaStructure {
    public ODBDGN_1 data1 = new ODBDGN_1();
    public ODBDGN_1 data2 = new ODBDGN_1();
    public ODBDGN_1 data3 = new ODBDGN_1();
    public ODBDGN_1 data4 = new ODBDGN_1();
    public ODBDGN_1 data5 = new ODBDGN_1();
    public ODBDGN_1 data6 = new ODBDGN_1();
    public ODBDGN_1 data7 = new ODBDGN_1();
  } // (sample) must be modified

  public static class ODBDGN_B extends JnaStructure {
    public ODBDGN_2 data1 = new ODBDGN_2();
    public ODBDGN_2 data2 = new ODBDGN_2();
    public ODBDGN_2 data3 = new ODBDGN_2();
    public ODBDGN_2 data4 = new ODBDGN_2();
    public ODBDGN_2 data5 = new ODBDGN_2();
    public ODBDGN_2 data6 = new ODBDGN_2();
    public ODBDGN_2 data7 = new ODBDGN_2();
  } // (sample) must be modified

  public static class ODBDGN_C extends JnaStructure {
    public ODBDGN_3 data1 = new ODBDGN_3();
    public ODBDGN_3 data2 = new ODBDGN_3();
    public ODBDGN_3 data3 = new ODBDGN_3();
    public ODBDGN_3 data4 = new ODBDGN_3();
    public ODBDGN_3 data5 = new ODBDGN_3();
    public ODBDGN_3 data6 = new ODBDGN_3();
    public ODBDGN_3 data7 = new ODBDGN_3();
  } // (sample) must be modified

  public static class ODBDGN_D extends JnaStructure {
    public ODBDGN_4 data1 = new ODBDGN_4();
    public ODBDGN_4 data2 = new ODBDGN_4();
    public ODBDGN_4 data3 = new ODBDGN_4();
    public ODBDGN_4 data4 = new ODBDGN_4();
    public ODBDGN_4 data5 = new ODBDGN_4();
    public ODBDGN_4 data6 = new ODBDGN_4();
    public ODBDGN_4 data7 = new ODBDGN_4();
  } // (sample) must be modified

  /* cnc_adcnv:read A/D conversion data */
  public static class ODBAD extends JnaStructure {
    public short datano; // input analog voltage type
    public short type; // analog voltage type
    public short data; // digital voltage data
  }

  //#if FS15D
  /* cnc_rdopmsg:read operator's message */
  public static class OPMSG_data extends JnaStructure {
    public short datano; // operator's message number
    public short type; // operator's message type
    public short char_num; // message string length

    public String data = StringHelper.repeatChar(' ', 129); // operator's message string
  } // In case that the data length is 129

  //#else
  /*public static class OPMSG_data
  {
    public short datano; // operator's message number
    public short type; // operator's message type
    public short char_num; // message string length

    public String data =StringHelper.repeatChar(' ', 256); // operator's message string
  } */// In case that the data length is 256
  //#endif
  public static class OPMSG extends JnaStructure {
    public OPMSG_data msg1 = new OPMSG_data();
    public OPMSG_data msg2 = new OPMSG_data();
    public OPMSG_data msg3 = new OPMSG_data();
    public OPMSG_data msg4 = new OPMSG_data();
    public OPMSG_data msg5 = new OPMSG_data();
  }

  /* cnc_rdopmsg2:read operator's message */
  public static class OPMSG2_data extends JnaStructure {
    public short datano; // operator's message number
    public short type; // operator's message type
    public short char_num; // message string length

    public String data = StringHelper.repeatChar(' ', 64); // operator's message string
  } // In case that the data length is 64

  public static class OPMSG2 extends JnaStructure {
    public OPMSG2_data msg1 = new OPMSG2_data();
    public OPMSG2_data msg2 = new OPMSG2_data();
    public OPMSG2_data msg3 = new OPMSG2_data();
    public OPMSG2_data msg4 = new OPMSG2_data();
    public OPMSG2_data msg5 = new OPMSG2_data();
  }

  /* cnc_rdopmsg3:read operator's message */
  public static class OPMSG3_data extends JnaStructure {
    public short datano; // operator's message number
    public short type; // operator's message type
    public short char_num; // message string length

    public String data = StringHelper.repeatChar(' ', 256); // operator's message string
  } // In case that the data length is 256

  public static class OPMSG3 extends JnaStructure {
    public OPMSG3_data msg1 = new OPMSG3_data();
    public OPMSG3_data msg2 = new OPMSG3_data();
    public OPMSG3_data msg3 = new OPMSG3_data();
    public OPMSG3_data msg4 = new OPMSG3_data();
    public OPMSG3_data msg5 = new OPMSG3_data();
  }

  /* cnc_sysconfig:read CNC configuration information */
  public static class ODBSYSC extends JnaStructure {

    public byte[] slot_no_p = new byte[16];

    public byte[] slot_no_l = new byte[16];

    public short[] mod_id = new short[16];

    public short[] soft_id = new short[16];

    public String s_series1 = StringHelper.repeatChar(' ', 5);

    public String s_series2 = StringHelper.repeatChar(' ', 5);

    public String s_series3 = StringHelper.repeatChar(' ', 5);

    public String s_series4 = StringHelper.repeatChar(' ', 5);

    public String s_series5 = StringHelper.repeatChar(' ', 5);

    public String s_series6 = StringHelper.repeatChar(' ', 5);

    public String s_series7 = StringHelper.repeatChar(' ', 5);

    public String s_series8 = StringHelper.repeatChar(' ', 5);

    public String s_series9 = StringHelper.repeatChar(' ', 5);

    public String s_series10 = StringHelper.repeatChar(' ', 5);

    public String s_series11 = StringHelper.repeatChar(' ', 5);

    public String s_series12 = StringHelper.repeatChar(' ', 5);

    public String s_series13 = StringHelper.repeatChar(' ', 5);

    public String s_series14 = StringHelper.repeatChar(' ', 5);

    public String s_series15 = StringHelper.repeatChar(' ', 5);

    public String s_series16 = StringHelper.repeatChar(' ', 5);

    public String s_version1 = StringHelper.repeatChar(' ', 5);

    public String s_version2 = StringHelper.repeatChar(' ', 5);

    public String s_version3 = StringHelper.repeatChar(' ', 5);

    public String s_version4 = StringHelper.repeatChar(' ', 5);

    public String s_version5 = StringHelper.repeatChar(' ', 5);

    public String s_version6 = StringHelper.repeatChar(' ', 5);

    public String s_version7 = StringHelper.repeatChar(' ', 5);

    public String s_version8 = StringHelper.repeatChar(' ', 5);

    public String s_version9 = StringHelper.repeatChar(' ', 5);

    public String s_version10 = StringHelper.repeatChar(' ', 5);

    public String s_version11 = StringHelper.repeatChar(' ', 5);

    public String s_version12 = StringHelper.repeatChar(' ', 5);

    public String s_version13 = StringHelper.repeatChar(' ', 5);

    public String s_version14 = StringHelper.repeatChar(' ', 5);

    public String s_version15 = StringHelper.repeatChar(' ', 5);

    public String s_version16 = StringHelper.repeatChar(' ', 5);

    public byte[] dummy = new byte[16];
    public short m_rom;
    public short s_rom;

    public char[] svo_soft = new char[6];

    public char[] pmc_soft = new char[6];

    public char[] lad_soft = new char[6];

    public char[] mcr_soft = new char[6];

    public char[] spl1_soft = new char[6];

    public char[] spl2_soft = new char[6];
    public short frmmin;
    public short drmmin;
    public short srmmin;
    public short pmcmin;
    public short crtmin;
    public short sv1min;
    public short sv3min;
    public short sicmin;
    public short posmin;
    public short drmmrc;
    public short drmarc;
    public short pmcmrc;
    public short dmaarc;
    public short iopt;
    public short hdiio;
    public short frmsub;
    public short drmsub;
    public short srmsub;
    public short sv5sub;
    public short sv7sub;
    public short sicsub;
    public short possub;
    public short hamsub;
    public short gm2gr1;
    public short crtgr2;
    public short gm1gr2;
    public short gm2gr2;
    public short cmmrb;
    public short sv5axs;
    public short sv7axs;
    public short sicaxs;
    public short posaxs;
    public short hanaxs;
    public short romr64;
    public short srmr64;
    public short dr1r64;
    public short dr2r64;
    public short iopio2;
    public short hdiio2;
    public short cmmrb2;
    public short romfap;
    public short srmfap;
    public short drmfap;
  }

  /* cnc_rdprstrinfo:read program restart information */
  public static class ODBPRS extends JnaStructure {
    public short datano; // dummy
    public short type; // dummy

    public short[] data_info = new short[5]; // data setting information
    public int rstr_bc; // block counter

    public int[] rstr_m = new int[35]; // M code value

    public int[] rstr_t = new int[2]; // T code value
    public int rstr_s; // S code value
    public int rstr_b; // B code value

    public int[] dest = new int[MAX_AXIS]; // program re-start position

    public int[] dist = new int[MAX_AXIS]; // program re-start distance
  }

  //#if FS15D
  /* cnc_rdopnlsgnl:read output signal image of software operator's panel */
  /* cnc_wropnlsgnl:write output signal of software operator's panel */
  public static class IODBSGNL extends JnaStructure {
    public short datano; // dummy
    public short type; // data select flag
    public short mode; // mode signal
    public short hndl_ax; // Manual handle feed axis selection signal
    public short hndl_mv; // Manual handle feed travel distance selection signal
    public short rpd_ovrd; // rapid traverse override signal
    public short jog_ovrd; // manual feedrate override signal
    public short feed_ovrd; // feedrate override signal
    public short spdl_ovrd; // spindle override signal
    public short blck_del; // optional block skip signal
    public short sngl_blck; // single block signal
    public short machn_lock; // machine lock signal
    public short dry_run; // dry run signal
    public short mem_prtct; // memory protection signal
    public short feed_hold; // automatic operation halt signal
    public short manual_rpd; // (not used)

    public short[] dummy = new short[2]; // (not used)
  }

  //#else
  /* cnc_rdopnlsgnl:read output signal image of software operator's panel */
  /* cnc_wropnlsgnl:write output signal of software operator's panel */
 /* public static class IODBSGNL
  {
    public short datano; // dummy
    public short type; // data select flag
    public short mode; // mode signal
    public short hndl_ax; // Manual handle feed axis selection signal
    public short hndl_mv; // Manual handle feed travel distance selection signal
    public short rpd_ovrd; // rapid traverse override signal
    public short jog_ovrd; // manual feedrate override signal
    public short feed_ovrd; // feedrate override signal
    public short spdl_ovrd; // (not used)
    public short blck_del; // optional block skip signal
    public short sngl_blck; // single block signal
    public short machn_lock; // machine lock signal
    public short dry_run; // dry run signal
    public short mem_prtct; // memory protection signal
    public short feed_hold; // automatic operation halt signal
  }*/
//#endif
  /* cnc_rdopnlgnrl:read general signal image of software operator's panel */
  /* cnc_wropnlgnrl:write general signal image of software operator's panel */
  public static class IODBGNRL extends JnaStructure {
    public short datano; // dummy
    public short type; // data select flag

    public byte sgnal; // general signal
  }

  /* cnc_rdopnlgsname:read general signal name of software operator's panel */
  /* cnc_wropnlgsname:write general signal name of software operator's panel*/
  public static class IODBRDNA extends JnaStructure {
    public short datano; // dummy
    public short type; // data select flag

    public String sgnl1_name = StringHelper.repeatChar(' ', 9); // general signal 1 name

    public String sgnl2_name = StringHelper.repeatChar(' ', 9); // general signal 2 name

    public String sgnl3_name = StringHelper.repeatChar(' ', 9); // general signal 3 name

    public String sgnl4_name = StringHelper.repeatChar(' ', 9); // general signal 4 name

    public String sgnl5_name = StringHelper.repeatChar(' ', 9); // general signal 5 name

    public String sgnl6_name = StringHelper.repeatChar(' ', 9); // general signal 6 name

    public String sgnl7_name = StringHelper.repeatChar(' ', 9); // general signal 7 name

    public String sgnl8_name = StringHelper.repeatChar(' ', 9); // general signal 8 name
  }

  /* cnc_getdtailerr:get detail error */
  public static class ODBERR extends JnaStructure {
    public short err_no;
    public short err_dtno;
  }

  /* cnc_rdparainfo:read informations of CNC parameter */
  public static class ODBPARAIF_info extends JnaStructure {
    public short prm_no;
    public short prm_type;
  }

  public static class ODBPARAIF1 extends JnaStructure {
    public ODBPARAIF_info info1 = new ODBPARAIF_info();
    public ODBPARAIF_info info2 = new ODBPARAIF_info();
    public ODBPARAIF_info info3 = new ODBPARAIF_info();
    public ODBPARAIF_info info4 = new ODBPARAIF_info();
    public ODBPARAIF_info info5 = new ODBPARAIF_info();
    public ODBPARAIF_info info6 = new ODBPARAIF_info();
    public ODBPARAIF_info info7 = new ODBPARAIF_info();
    public ODBPARAIF_info info8 = new ODBPARAIF_info();
    public ODBPARAIF_info info9 = new ODBPARAIF_info();
    public ODBPARAIF_info info10 = new ODBPARAIF_info();
  } // In case that the number of data is 10

  public static class ODBPARAIF extends JnaStructure {

    public short info_no;
    public short prev_no;
    public short next_no;
    public ODBPARAIF1 info = new ODBPARAIF1();
  }

  /* cnc_rdsetinfo:read informations of CNC setting data */
  public static class ODBSETIF_info extends JnaStructure {
    public short set_no;
    public short set_type;
  }

  public static class ODBSETIF1 extends JnaStructure {
    public ODBSETIF_info info1 = new ODBSETIF_info();
    public ODBSETIF_info info2 = new ODBSETIF_info();
    public ODBSETIF_info info3 = new ODBSETIF_info();
    public ODBSETIF_info info4 = new ODBSETIF_info();
    public ODBSETIF_info info5 = new ODBSETIF_info();
    public ODBSETIF_info info6 = new ODBSETIF_info();
    public ODBSETIF_info info7 = new ODBSETIF_info();
    public ODBSETIF_info info8 = new ODBSETIF_info();
    public ODBSETIF_info info9 = new ODBSETIF_info();
    public ODBSETIF_info info10 = new ODBSETIF_info();
  } // In case that the number of data is 10

  public static class ODBSETIF extends JnaStructure {

    public short info_no;
    public short prev_no;
    public short next_no;
    public ODBSETIF1 info = new ODBSETIF1();
  }

  /* cnc_rddiaginfo:read informations of CNC diagnose data */
  public static class ODBDIAGIF_info extends JnaStructure {
    public short diag_no;
    public short diag_type;
  }

  public static class ODBDIAGIF1 extends JnaStructure {
    public ODBDIAGIF_info info1 = new ODBDIAGIF_info();
    public ODBDIAGIF_info info2 = new ODBDIAGIF_info();
    public ODBDIAGIF_info info3 = new ODBDIAGIF_info();
    public ODBDIAGIF_info info4 = new ODBDIAGIF_info();
    public ODBDIAGIF_info info5 = new ODBDIAGIF_info();
    public ODBDIAGIF_info info6 = new ODBDIAGIF_info();
    public ODBDIAGIF_info info7 = new ODBDIAGIF_info();
    public ODBDIAGIF_info info8 = new ODBDIAGIF_info();
    public ODBDIAGIF_info info9 = new ODBDIAGIF_info();
    public ODBDIAGIF_info info10 = new ODBDIAGIF_info();
  } // In case that the number of data is 10

  public static class ODBDIAGIF extends JnaStructure {

    public short info_no;
    public short prev_no;
    public short next_no;
    public ODBDIAGIF1 info = new ODBDIAGIF1();
  }

  /* cnc_rdparanum:read maximum, minimum and total number of CNC parameter */
  public static class ODBPARANUM extends JnaStructure {
    public short para_min;
    public short para_max;
    public short total_no;
  }

  /* cnc_rdsetnum:read maximum, minimum and total number of CNC setting data */
  public static class ODBSETNUM extends JnaStructure {

    public short set_min;

    public short set_max;

    public short total_no;
  }

  /* cnc_rddiagnum:read maximum, minimum and total number of CNC diagnose data */
  public static class ODBDIAGNUM extends JnaStructure {

    public short diag_min;

    public short diag_max;

    public short total_no;
  }

  /* cnc_rdfrominfo:read F-ROM information on CNC  */
  public static class ODBFINFO_info extends JnaStructure {

    public String sysname = StringHelper.repeatChar(' ', 12); // F-ROM SYSTEM data Name
    public int fromsize; // F-ROM Size
  }

  public static class ODBFINFO1 extends JnaStructure {
    public ODBFINFO_info info1 = new ODBFINFO_info();
    public ODBFINFO_info info2 = new ODBFINFO_info();
    public ODBFINFO_info info3 = new ODBFINFO_info();
    public ODBFINFO_info info4 = new ODBFINFO_info();
    public ODBFINFO_info info5 = new ODBFINFO_info();
    public ODBFINFO_info info6 = new ODBFINFO_info();
    public ODBFINFO_info info7 = new ODBFINFO_info();
    public ODBFINFO_info info8 = new ODBFINFO_info();
    public ODBFINFO_info info9 = new ODBFINFO_info();
    public ODBFINFO_info info10 = new ODBFINFO_info();
    public ODBFINFO_info info11 = new ODBFINFO_info();
    public ODBFINFO_info info12 = new ODBFINFO_info();
    public ODBFINFO_info info13 = new ODBFINFO_info();
    public ODBFINFO_info info14 = new ODBFINFO_info();
    public ODBFINFO_info info15 = new ODBFINFO_info();
    public ODBFINFO_info info16 = new ODBFINFO_info();
    public ODBFINFO_info info17 = new ODBFINFO_info();
    public ODBFINFO_info info18 = new ODBFINFO_info();
    public ODBFINFO_info info19 = new ODBFINFO_info();
    public ODBFINFO_info info20 = new ODBFINFO_info();
    public ODBFINFO_info info21 = new ODBFINFO_info();
    public ODBFINFO_info info22 = new ODBFINFO_info();
    public ODBFINFO_info info23 = new ODBFINFO_info();
    public ODBFINFO_info info24 = new ODBFINFO_info();
    public ODBFINFO_info info25 = new ODBFINFO_info();
    public ODBFINFO_info info26 = new ODBFINFO_info();
    public ODBFINFO_info info27 = new ODBFINFO_info();
    public ODBFINFO_info info28 = new ODBFINFO_info();
    public ODBFINFO_info info29 = new ODBFINFO_info();
    public ODBFINFO_info info30 = new ODBFINFO_info();
    public ODBFINFO_info info31 = new ODBFINFO_info();
    public ODBFINFO_info info32 = new ODBFINFO_info();
  }

  public static class ODBFINFO extends JnaStructure {

    public String slotname = StringHelper.repeatChar(' ', 12); // Slot Name
    public int fromnum; // Number of F-ROM SYSTEM data
    public ODBFINFO1 info = new ODBFINFO1();
  }

  /* cnc_getfrominfo:read F-ROM information on CNC  */
  public static class ODBFINFORM_info extends JnaStructure {

    public String sysname = StringHelper.repeatChar(' ', 12); // F-ROM SYSTEM data Name
    public int fromsize; // F-ROM Size
    public int fromattrib; // F-ROM data attribute
  }

  public static class ODBFINFORM1 extends JnaStructure {
    public ODBFINFORM_info info1 = new ODBFINFORM_info();
    public ODBFINFORM_info info2 = new ODBFINFORM_info();
    public ODBFINFORM_info info3 = new ODBFINFORM_info();
    public ODBFINFORM_info info4 = new ODBFINFORM_info();
    public ODBFINFORM_info info5 = new ODBFINFORM_info();
    public ODBFINFORM_info info6 = new ODBFINFORM_info();
    public ODBFINFORM_info info7 = new ODBFINFORM_info();
    public ODBFINFORM_info info8 = new ODBFINFORM_info();
    public ODBFINFORM_info info9 = new ODBFINFORM_info();
    public ODBFINFORM_info info10 = new ODBFINFORM_info();
    public ODBFINFORM_info info11 = new ODBFINFORM_info();
    public ODBFINFORM_info info12 = new ODBFINFORM_info();
    public ODBFINFORM_info info13 = new ODBFINFORM_info();
    public ODBFINFORM_info info14 = new ODBFINFORM_info();
    public ODBFINFORM_info info15 = new ODBFINFORM_info();
    public ODBFINFORM_info info16 = new ODBFINFORM_info();
    public ODBFINFORM_info info17 = new ODBFINFORM_info();
    public ODBFINFORM_info info18 = new ODBFINFORM_info();
    public ODBFINFORM_info info19 = new ODBFINFORM_info();
    public ODBFINFORM_info info20 = new ODBFINFORM_info();
    public ODBFINFORM_info info21 = new ODBFINFORM_info();
    public ODBFINFORM_info info22 = new ODBFINFORM_info();
    public ODBFINFORM_info info23 = new ODBFINFORM_info();
    public ODBFINFORM_info info24 = new ODBFINFORM_info();
    public ODBFINFORM_info info25 = new ODBFINFORM_info();
    public ODBFINFORM_info info26 = new ODBFINFORM_info();
    public ODBFINFORM_info info27 = new ODBFINFORM_info();
    public ODBFINFORM_info info28 = new ODBFINFORM_info();
    public ODBFINFORM_info info29 = new ODBFINFORM_info();
    public ODBFINFORM_info info30 = new ODBFINFORM_info();
    public ODBFINFORM_info info31 = new ODBFINFORM_info();
    public ODBFINFORM_info info32 = new ODBFINFORM_info();
  }

  public static class ODBFINFORM extends JnaStructure {
    public int slotno; // Slot Number

    public String slotname = StringHelper.repeatChar(' ', 12); // Slot Name
    public int fromnum; // Number of F-ROM SYSTEM data
    public ODBFINFORM1 info = new ODBFINFORM1();
  }

  /* cnc_rdsraminfo:read S-RAM information on CNC */
  /* cnc_getsraminfo:read S-RAM information on CNC */
  public static class ODBSINFO_info extends JnaStructure {

    public String sramname = StringHelper.repeatChar(' ', 12); // S-RAM data Name
    public int sramsize; // S-RAM data Size
    public short divnumber; // Division number of S-RAM file

    public String fname1 = StringHelper.repeatChar(' ', 16); // S-RAM data Name1

    public String fname2 = StringHelper.repeatChar(' ', 16); // S-RAM data Name2

    public String fname3 = StringHelper.repeatChar(' ', 16); // S-RAM data Name3

    public String fname4 = StringHelper.repeatChar(' ', 16); // S-RAM data Name4

    public String fname5 = StringHelper.repeatChar(' ', 16); // S-RAM data Name5

    public String fname6 = StringHelper.repeatChar(' ', 16); // S-RAM data Name6
  }

  public static class ODBSINFO1 extends JnaStructure {
    public ODBSINFO_info info1 = new ODBSINFO_info();
    public ODBSINFO_info info2 = new ODBSINFO_info();
    public ODBSINFO_info info3 = new ODBSINFO_info();
    public ODBSINFO_info info4 = new ODBSINFO_info();
    public ODBSINFO_info info5 = new ODBSINFO_info();
    public ODBSINFO_info info6 = new ODBSINFO_info();
    public ODBSINFO_info info7 = new ODBSINFO_info();
    public ODBSINFO_info info8 = new ODBSINFO_info();
  }

  public static class ODBSINFO extends JnaStructure {
    public int sramnum; // Number of S-RAM data
    public ODBSINFO1 info = new ODBSINFO1();
  }

  /* cnc_rdsramaddr:read S-RAM address on CNC */
  public static class SRAMADDR extends JnaStructure {
    public short type; // SRAM data type
    public int size; // SRAM data size
    public int offset; // offset from top address of SRAM
  }

  /* cnc_dtsvrdpgdir:read file directory in Data Server */
  public static class ODBDSDIR_data extends JnaStructure {

    public String file_name = StringHelper.repeatChar(' ', 16);

    public String comment = StringHelper.repeatChar(' ', 64);
    public int size;

    public String date = StringHelper.repeatChar(' ', 16);
  }

  public static class ODBDSDIR1 extends JnaStructure {
    public ODBDSDIR_data data1 = new ODBDSDIR_data();
    public ODBDSDIR_data data2 = new ODBDSDIR_data();
    public ODBDSDIR_data data3 = new ODBDSDIR_data();
    public ODBDSDIR_data data4 = new ODBDSDIR_data();
    public ODBDSDIR_data data5 = new ODBDSDIR_data();
    public ODBDSDIR_data data6 = new ODBDSDIR_data();
    public ODBDSDIR_data data7 = new ODBDSDIR_data();
    public ODBDSDIR_data data8 = new ODBDSDIR_data();
    public ODBDSDIR_data data9 = new ODBDSDIR_data();
    public ODBDSDIR_data data10 = new ODBDSDIR_data();
    public ODBDSDIR_data data11 = new ODBDSDIR_data();
    public ODBDSDIR_data data12 = new ODBDSDIR_data();
    public ODBDSDIR_data data13 = new ODBDSDIR_data();
    public ODBDSDIR_data data14 = new ODBDSDIR_data();
    public ODBDSDIR_data data15 = new ODBDSDIR_data();
    public ODBDSDIR_data data16 = new ODBDSDIR_data();
    public ODBDSDIR_data data17 = new ODBDSDIR_data();
    public ODBDSDIR_data data18 = new ODBDSDIR_data();
    public ODBDSDIR_data data19 = new ODBDSDIR_data();
    public ODBDSDIR_data data20 = new ODBDSDIR_data();
    public ODBDSDIR_data data21 = new ODBDSDIR_data();
    public ODBDSDIR_data data22 = new ODBDSDIR_data();
    public ODBDSDIR_data data23 = new ODBDSDIR_data();
    public ODBDSDIR_data data24 = new ODBDSDIR_data();
    public ODBDSDIR_data data25 = new ODBDSDIR_data();
    public ODBDSDIR_data data26 = new ODBDSDIR_data();
    public ODBDSDIR_data data27 = new ODBDSDIR_data();
    public ODBDSDIR_data data28 = new ODBDSDIR_data();
    public ODBDSDIR_data data29 = new ODBDSDIR_data();
    public ODBDSDIR_data data30 = new ODBDSDIR_data();
    public ODBDSDIR_data data31 = new ODBDSDIR_data();
    public ODBDSDIR_data data32 = new ODBDSDIR_data();
  }

  public static class ODBDSDIR extends JnaStructure {
    public int file_num;
    public int remainder;
    public short data_num;
    public ODBDSDIR1 data = new ODBDSDIR1();
  }

  /* cnc_dtsvrdset:read setting data for Data Server */
  /* cnc_dtsvwrset:write setting data for Data Server */
  public static class IODBDSSET extends JnaStructure {

    public String host_ip = StringHelper.repeatChar(' ', 16);

    public String host_uname = StringHelper.repeatChar(' ', 32);

    public String host_passwd = StringHelper.repeatChar(' ', 32);

    public String host_dir = StringHelper.repeatChar(' ', 128);

    public String dtsv_mac = StringHelper.repeatChar(' ', 13);

    public String dtsv_ip = StringHelper.repeatChar(' ', 16);

    public String dtsv_mask = StringHelper.repeatChar(' ', 16);
  }

  /* cnc_dtsvmntinfo:read maintenance information for Data Server */
  public static class ODBDSMNT extends JnaStructure {
    public int empty_cnt;
    public int total_size;
    public int read_ptr;
    public int write_ptr;
  }

  /* cnc_rdposerrs2:read the position deviation S1 and S2 */
  public static class ODBPSER extends JnaStructure {
    public int poserr1;
    public int poserr2;
  }

  /* cnc_rdctrldi:read the control input signal */
  public static class ODBSPDI_data extends JnaStructure {

    public byte sgnl1;

    public byte sgnl2;

    public byte sgnl3;

    public byte sgnl4;
  }

  public static class ODBSPDI extends JnaStructure {
    public ODBSPDI_data di1 = new ODBSPDI_data();
    public ODBSPDI_data di2 = new ODBSPDI_data();
    public ODBSPDI_data di3 = new ODBSPDI_data();
    public ODBSPDI_data di4 = new ODBSPDI_data();
  }

  /* cnc_rdctrldo:read the control output signal */
  public static class ODBSPDO_data extends JnaStructure {

    public byte sgnl1;

    public byte sgnl2;

    public byte sgnl3;

    public byte sgnl4;
  }

  public static class ODBSPDO extends JnaStructure {
    public ODBSPDO_data do1 = new ODBSPDO_data();
    public ODBSPDO_data do2 = new ODBSPDO_data();
    public ODBSPDO_data do3 = new ODBSPDO_data();
    public ODBSPDO_data do4 = new ODBSPDO_data();
  }

  /* cnc_rdwaveprm:read the parameter of wave diagnosis */
  /* cnc_wrwaveprm:write the parameter of wave diagnosis */
  public static class IODBWAVE_io extends JnaStructure {

    public byte adr;

    public byte bit;
    public short no;
  }

  public static class IODBWAVE_axis extends JnaStructure {
    public short axis;
  }

  public static class IODBWAVE_u extends JnaStructure {

    public IODBWAVE_io io = new IODBWAVE_io();

    public IODBWAVE_axis axis = new IODBWAVE_axis();
  }

  public static class IODBWAVE_ch_data extends JnaStructure {
    public short kind;
    public IODBWAVE_u u = new IODBWAVE_u();
  }

  public static class IODBWAVE_ch extends JnaStructure {
    public IODBWAVE_ch_data ch1 = new IODBWAVE_ch_data();
    public IODBWAVE_ch_data ch2 = new IODBWAVE_ch_data();
    public IODBWAVE_ch_data ch3 = new IODBWAVE_ch_data();
    public IODBWAVE_ch_data ch4 = new IODBWAVE_ch_data();
    public IODBWAVE_ch_data ch5 = new IODBWAVE_ch_data();
    public IODBWAVE_ch_data ch6 = new IODBWAVE_ch_data();
    public IODBWAVE_ch_data ch7 = new IODBWAVE_ch_data();
    public IODBWAVE_ch_data ch8 = new IODBWAVE_ch_data();
    public IODBWAVE_ch_data ch9 = new IODBWAVE_ch_data();
    public IODBWAVE_ch_data ch10 = new IODBWAVE_ch_data();
    public IODBWAVE_ch_data ch11 = new IODBWAVE_ch_data();
    public IODBWAVE_ch_data ch12 = new IODBWAVE_ch_data();
  }

  public static class IODBWAVE extends JnaStructure {
    public short condition;
    public char trg_adr;

    public byte trg_bit;
    public short trg_no;
    public short delay;
    public short t_range;
    public IODBWAVE_ch ch = new IODBWAVE_ch();
  }

  /* cnc_rdwaveprm2:read the parameter of wave diagnosis 2 */
  /* cnc_wrwaveprm2:write the parameter of wave diagnosis 2 */
  public static class IODBWVPRM_io extends JnaStructure {

    public byte adr;

    public byte bit;
    public short no;
  }

  public static class IODBWVPRM_axis extends JnaStructure {
    public short axis;
  }

  public static class IODBWVPRM_u extends JnaStructure {

    public IODBWVPRM_io io = new IODBWVPRM_io();

    public IODBWVPRM_axis axis = new IODBWVPRM_axis();
  }

  public static class IODBWVPRM_ch_data extends JnaStructure {
    public short kind;
    public IODBWVPRM_u u = new IODBWVPRM_u();
    public int reserve2;
  }

  public static class IODBWVPRM_ch extends JnaStructure {
    public IODBWVPRM_ch_data ch1 = new IODBWVPRM_ch_data();
    public IODBWVPRM_ch_data ch2 = new IODBWVPRM_ch_data();
    public IODBWVPRM_ch_data ch3 = new IODBWVPRM_ch_data();
    public IODBWVPRM_ch_data ch4 = new IODBWVPRM_ch_data();
    public IODBWVPRM_ch_data ch5 = new IODBWVPRM_ch_data();
    public IODBWVPRM_ch_data ch6 = new IODBWVPRM_ch_data();
    public IODBWVPRM_ch_data ch7 = new IODBWVPRM_ch_data();
    public IODBWVPRM_ch_data ch8 = new IODBWVPRM_ch_data();
    public IODBWVPRM_ch_data ch9 = new IODBWVPRM_ch_data();
    public IODBWVPRM_ch_data ch10 = new IODBWVPRM_ch_data();
    public IODBWVPRM_ch_data ch11 = new IODBWVPRM_ch_data();
    public IODBWVPRM_ch_data ch12 = new IODBWVPRM_ch_data();
  }

  public static class IODBWVPRM extends JnaStructure {
    public short condition;

    public byte trg_adr;

    public byte trg_bit;
    public short trg_no;
    public short reserve1;
    public int delay;
    public int t_range;
    public IODBWVPRM_ch ch = new IODBWVPRM_ch();
  }

  /* cnc_rdwavedata:read the data of wave diagnosis */
  public static class ODBWVDT_io extends JnaStructure {

    public byte adr;

    public byte bit;
    public short no;
  }

  public static class ODBWVDT_axis extends JnaStructure {
    public short axis;
  }

  public static class ODBWVDT_u extends JnaStructure {

    public ODBWVDT_io io = new ODBWVDT_io();

    public ODBWVDT_axis axis = new ODBWVDT_axis();
  }

  public static class ODBWVDT extends JnaStructure {
    public short channel;
    public short kind;
    public ODBWVDT_u u = new ODBWVDT_u();

    public byte year;

    public byte month;

    public byte day;

    public byte hour;

    public byte minute;

    public byte second;
    public short t_cycle;

    public short[] data = new short[8192];
  }

  /* cnc_rdrmtwaveprm:read the parameter of wave diagnosis for remort diagnosis */
  /* cnc_wrrmtwaveprm:write the parameter of wave diagnosis for remort diagnosis */
  public static class IODBRMTPRM_alm extends JnaStructure {
    public short no;
    public byte axis;

    public byte type;
  }

  public static class IODBRMTPRM_io extends JnaStructure {
    public char adr;

    public byte bit;
    public short no;
  }

  public static class IODBRMTPRM_trg extends JnaStructure {

    public IODBRMTPRM_alm alm = new IODBRMTPRM_alm();

    public IODBRMTPRM_io io = new IODBRMTPRM_io();
  }

  public static class IODBRMTPRM_smpl extends JnaStructure {
    public char adr;

    public byte bit;
    public short no;
  }

  public static class IODBRMTPRM1 extends JnaStructure {
    public IODBRMTPRM_smpl ampl1 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl2 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl3 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl4 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl5 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl6 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl7 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl8 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl9 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl10 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl11 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl12 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl13 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl14 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl15 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl16 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl17 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl18 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl19 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl20 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl21 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl22 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl23 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl24 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl25 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl26 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl27 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl28 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl29 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl30 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl31 = new IODBRMTPRM_smpl();
    public IODBRMTPRM_smpl ampl32 = new IODBRMTPRM_smpl();
  }

  public static class IODBRMTPRM extends JnaStructure {
    public short condition;
    public short reserve;
    public IODBRMTPRM_trg trg = new IODBRMTPRM_trg();
    public int delay;
    public short wv_intrvl;
    public short io_intrvl;
    public short kind1;
    public short kind2;
    public IODBRMTPRM1 ampl = new IODBRMTPRM1();
  }

  /* cnc_rdrmtwavedt:read the data of wave diagnosis for remort diagnosis */
  public static class ODBRMTDT extends JnaStructure {
    public short channel;
    public short kind;

    public byte year;

    public byte month;

    public byte day;

    public byte hour;

    public byte minute;

    public byte second;
    public short t_intrvl;
    public short trg_data;
    public int ins_ptr;
    public short t_delta;

    public short[] data = new short[1917];
  }

  /* cnc_rdsavsigadr:read of address for PMC signal batch save */
  /* cnc_wrsavsigadr:write of address for PMC signal batch save */
  public static class IODBSIGAD extends JnaStructure {

    public byte adr;

    public byte reserve;
    public short no;
    public short size;
  }

  /* cnc_rdmgrpdata:read M-code group data */
  public static class ODBMGRP_data extends JnaStructure {
    public int m_code;
    public short grp_no;

    public String m_name = StringHelper.repeatChar(' ', 21);

    public byte dummy;
  }

  public static class ODBMGRP extends JnaStructure {
    public ODBMGRP_data mgrp1 = new ODBMGRP_data();
    public ODBMGRP_data mgrp2 = new ODBMGRP_data();
    public ODBMGRP_data mgrp3 = new ODBMGRP_data();
    public ODBMGRP_data mgrp4 = new ODBMGRP_data();
    public ODBMGRP_data mgrp5 = new ODBMGRP_data();
    public ODBMGRP_data mgrp6 = new ODBMGRP_data();
    public ODBMGRP_data mgrp7 = new ODBMGRP_data();
    public ODBMGRP_data mgrp8 = new ODBMGRP_data();
    public ODBMGRP_data mgrp9 = new ODBMGRP_data();
    public ODBMGRP_data mgrp10 = new ODBMGRP_data();
  }

  /* cnc_wrmgrpdata:write M-code group data */
  public static class IDBMGRP extends JnaStructure {
    public short s_no;
    public short dummy;
    public short num;

    public short[] group = new short[500];
  }

  /* cnc_rdexecmcode:read executing M-code group data */
  public static class ODBEXEM_data extends JnaStructure {
    public int no;
    public short flag;
  }

  public static class ODBEXEM1 extends JnaStructure {
    public ODBEXEM_data m_code1 = new ODBEXEM_data();
    public ODBEXEM_data m_code2 = new ODBEXEM_data();
    public ODBEXEM_data m_code3 = new ODBEXEM_data();
    public ODBEXEM_data m_code4 = new ODBEXEM_data();
    public ODBEXEM_data m_code5 = new ODBEXEM_data();
  }

  public static class ODBEXEM extends JnaStructure {
    public short grp_no;
    public short mem_no;
    public ODBEXEM1 m_code = new ODBEXEM1();

    public String m_name = StringHelper.repeatChar(' ', 21);

    public byte dummy;
  }

  /* cnc_rdrstrmcode:read program restart M-code group data */
  public static class M_CODE_data extends JnaStructure {
    public int no;
    public short flag;
  }

  public static class M_CODE1 extends JnaStructure {
    public M_CODE_data m_code1 = new M_CODE_data();
    public M_CODE_data m_code2 = new M_CODE_data();
    public M_CODE_data m_code3 = new M_CODE_data();
    public M_CODE_data m_code4 = new M_CODE_data();
    public M_CODE_data m_code5 = new M_CODE_data();
  }

  public static class ODBRSTRM extends JnaStructure {
    public short grp_no;
    public short mem_no;
    public M_CODE1 m_code = new M_CODE1();
  }

  /* cnc_rdproctime:read processing time stamp data */
  public static class ODBPTIME_data extends JnaStructure {
    public int prg_no;
    public short hour;

    public byte minute;

    public byte second;
  }

  public static class ODBPTIME1 extends JnaStructure {
    public ODBPTIME_data data1 = new ODBPTIME_data();
    public ODBPTIME_data data2 = new ODBPTIME_data();
    public ODBPTIME_data data3 = new ODBPTIME_data();
    public ODBPTIME_data data4 = new ODBPTIME_data();
    public ODBPTIME_data data5 = new ODBPTIME_data();
    public ODBPTIME_data data6 = new ODBPTIME_data();
    public ODBPTIME_data data7 = new ODBPTIME_data();
    public ODBPTIME_data data8 = new ODBPTIME_data();
    public ODBPTIME_data data9 = new ODBPTIME_data();
    public ODBPTIME_data data10 = new ODBPTIME_data();
  } // In case that the number of data is 10

  public static class ODBPTIME extends JnaStructure {
    public short num;
    public ODBPTIME1 data = new ODBPTIME1();
  }

  /* cnc_rdprgdirtime:read program directory for processing time data */
  public static class PRGDIRTM_data extends JnaStructure {
    public int prg_no;

    public String comment = StringHelper.repeatChar(' ', 51);

    public String cuttime = StringHelper.repeatChar(' ', 13);
  }

  public static class PRGDIRTM extends JnaStructure {
    public PRGDIRTM_data data1 = new PRGDIRTM_data();
    public PRGDIRTM_data data2 = new PRGDIRTM_data();
    public PRGDIRTM_data data3 = new PRGDIRTM_data();
    public PRGDIRTM_data data4 = new PRGDIRTM_data();
    public PRGDIRTM_data data5 = new PRGDIRTM_data();
    public PRGDIRTM_data data6 = new PRGDIRTM_data();
    public PRGDIRTM_data data7 = new PRGDIRTM_data();
    public PRGDIRTM_data data8 = new PRGDIRTM_data();
    public PRGDIRTM_data data9 = new PRGDIRTM_data();
    public PRGDIRTM_data data10 = new PRGDIRTM_data();
  } // In case that the number of data is 10

  /* cnc_rdprogdir2:read program directory 2 */
//#if (!ONO8D)
  public static class PRGDIR2_data extends JnaStructure {
    public short number;
    public int length;

    public String comment = StringHelper.repeatChar(' ', 51);

    public byte dummy;
  }

  //#else
  /*public static class PRGDIR2_data
  {
    public int number;
    public int length;

    public String comment =StringHelper.repeatChar(' ',51);

    public byte dummy;
  }*/
  //#endif
  public static class PRGDIR2 extends JnaStructure {
    public PRGDIR2_data dir1 = new PRGDIR2_data();
    public PRGDIR2_data dir2 = new PRGDIR2_data();
    public PRGDIR2_data dir3 = new PRGDIR2_data();
    public PRGDIR2_data dir4 = new PRGDIR2_data();
    public PRGDIR2_data dir5 = new PRGDIR2_data();
    public PRGDIR2_data dir6 = new PRGDIR2_data();
    public PRGDIR2_data dir7 = new PRGDIR2_data();
    public PRGDIR2_data dir8 = new PRGDIR2_data();
    public PRGDIR2_data dir9 = new PRGDIR2_data();
    public PRGDIR2_data dir10 = new PRGDIR2_data();
  } // In case that the number of data is 10

  /* cnc_rdprogdir3:read program directory 3 */
  public static class DIR3_MDATE extends JnaStructure {
    public short year;
    public short month;
    public short day;
    public short hour;
    public short minute;
    public short dummy;
  }

  public static class DIR3_CDATE extends JnaStructure {
    public short year;
    public short month;
    public short day;
    public short hour;
    public short minute;
    public short dummy;
  }

  public static class PRGDIR3_data extends JnaStructure {
    public int number;
    public int length;
    public int page;

    public String comment = StringHelper.repeatChar(' ', 52);
    public DIR3_MDATE mdate = new DIR3_MDATE();
    public DIR3_CDATE cdate = new DIR3_CDATE();
  }

  public static class PRGDIR3 extends JnaStructure {
    public PRGDIR3_data dir1 = new PRGDIR3_data();
    public PRGDIR3_data dir2 = new PRGDIR3_data();
    public PRGDIR3_data dir3 = new PRGDIR3_data();
    public PRGDIR3_data dir4 = new PRGDIR3_data();
    public PRGDIR3_data dir5 = new PRGDIR3_data();
    public PRGDIR3_data dir6 = new PRGDIR3_data();
    public PRGDIR3_data dir7 = new PRGDIR3_data();
    public PRGDIR3_data dir8 = new PRGDIR3_data();
    public PRGDIR3_data dir9 = new PRGDIR3_data();
    public PRGDIR3_data dir10 = new PRGDIR3_data();
  } // In case that the number of data is 10

  /* cnc_rdprogdir4:read program directory 4 */
  public static class DIR4_MDATE extends JnaStructure {
    public short year;
    public short month;
    public short day;
    public short hour;
    public short minute;
    public short dummy;
  }

  public static class DIR4_CDATE extends JnaStructure {
    public short year;
    public short month;
    public short day;
    public short hour;
    public short minute;
    public short dummy;
  }

  public static class PRGDIR4_data extends JnaStructure {
    public int number;
    public int length;
    public int page;

    public String comment = StringHelper.repeatChar(' ', 52);
    public DIR4_MDATE mdate = new DIR4_MDATE();
    public DIR4_CDATE cdate = new DIR4_CDATE();
  }

  public static class PRGDIR4 extends JnaStructure {
    public PRGDIR4_data dir1 = new PRGDIR4_data();
    public PRGDIR4_data dir2 = new PRGDIR4_data();
    public PRGDIR4_data dir3 = new PRGDIR4_data();
    public PRGDIR4_data dir4 = new PRGDIR4_data();
    public PRGDIR4_data dir5 = new PRGDIR4_data();
    public PRGDIR4_data dir6 = new PRGDIR4_data();
    public PRGDIR4_data dir7 = new PRGDIR4_data();
    public PRGDIR4_data dir8 = new PRGDIR4_data();
    public PRGDIR4_data dir9 = new PRGDIR4_data();
    public PRGDIR4_data dir10 = new PRGDIR4_data();
  } // In case that the number of data is 10

  /* cnc_rdcomparam:read communication parameter for DNC1, DNC2, OSI-Ethernet */
  /* cnc_wrcomparam:write communication parameter for DNC1, DNC2, OSI-Ethernet */
  public static class IODBCPRM extends JnaStructure {

    public String NcApli = StringHelper.repeatChar(' ', 65);

    public byte Dummy1;

    public String HostApli = StringHelper.repeatChar(' ', 65);

    public byte Dummy2;

    public int StatPstv;

    public int StatNgtv;

    public int Statmask;

    public int AlarmStat;

    public int PsclHaddr;

    public int PsclLaddr;

    public short SvcMode1;

    public short SvcMode2;
    public int FileTout;
    public int RemTout;
  }

  /* cnc_rdintchk:read interference check */
  /* cnc_wrintchk:write interference check */
  public static class IODBINT extends JnaStructure {
    public short datano_s; // start offset No.
    public short type; // kind of position
    public short datano_e; // end offset No.

    public int[] data = new int[8 * 3]; // position value of area for not attach
  }

  /* cnc_rdwkcdshft:read work coordinate shift */
  /* cnc_wrwkcdshft:write work coordinate shift */
  /* cnc_rdwkcdsfms:read work coordinate shift measure */
  /* cnc_wrwkcdsfms:write work coordinate shift measure */
  public static class IODBWCSF extends JnaStructure {
    public short datano; // datano
    public short type; // axis number

    public int[] data = new int[MAX_AXIS]; // data
  }

  /* cnc_rdomhisinfo:read operator message history information */
  public static class ODBOMIF extends JnaStructure {

    public short om_max; // maximum operator message history

    public short om_sum; // actually operator message history

    public short om_char; // maximum character (include NULL)
  }

  /* cnc_rdomhistry:read operator message history */
  public static class ODBOMHIS_data extends JnaStructure {
    public short om_no; // operator message number
    public short year; // year
    public short month; // month
    public short day; // day
    public short hour; // hour
    public short minute; // mimute
    public short second; // second

    public String om_msg = StringHelper.repeatChar(' ', 256);
  }

  public static class ODBOMHIS extends JnaStructure {
    public ODBOMHIS_data omhis1 = new ODBOMHIS_data();
    public ODBOMHIS_data omhis2 = new ODBOMHIS_data();
    public ODBOMHIS_data omhis3 = new ODBOMHIS_data();
    public ODBOMHIS_data omhis4 = new ODBOMHIS_data();
    public ODBOMHIS_data omhis5 = new ODBOMHIS_data();
    public ODBOMHIS_data omhis6 = new ODBOMHIS_data();
    public ODBOMHIS_data omhis7 = new ODBOMHIS_data();
    public ODBOMHIS_data omhis8 = new ODBOMHIS_data();
    public ODBOMHIS_data omhis9 = new ODBOMHIS_data();
    public ODBOMHIS_data omhis10 = new ODBOMHIS_data();
  } // In case that the number of data is 10

  /* cnc_rdbtofsr:read b-axis tool offset value(area specified) */
  /* cnc_wrbtofsr:write b-axis tool offset value(area specified) */
  public static class IODBBTO extends JnaStructure {
    public short datano_s; // start offset number
    public short type; // offset type
    public short datano_e; // end offset number

    public int[] ofs = new int[18]; // offset
  } // In case that the number of data is 9 (B type)

  /* cnc_rdbtofsinfo:read b-axis tool offset information */
  public static class ODBBTLINF extends JnaStructure {
    public short ofs_type; // memory type
    public short use_no; // sum of b-axis offset
    public short sub_no; // sub function number of offset cancel
  }

  /* cnc_rdbaxis:read b-axis command */
  public static class ODBBAXIS extends JnaStructure {
    public short flag; // b-axis command exist or not
    public short command; // b-axis command

    public short speed; // b-axis speed
    public int sub_data; // b-axis sub data
  }

  /* cnc_rdsyssoft:read CNC system soft series and version */
  public static class ODBSYSS extends JnaStructure {

    public byte[] slot_no_p = new byte[16];

    public byte[] slot_no_l = new byte[16];

    public short[] module_id = new short[16];

    public short[] soft_id = new short[16];

    public String soft_series1 = StringHelper.repeatChar(' ', 5);

    public String soft_series2 = StringHelper.repeatChar(' ', 5);

    public String soft_series3 = StringHelper.repeatChar(' ', 5);

    public String soft_series4 = StringHelper.repeatChar(' ', 5);

    public String soft_series5 = StringHelper.repeatChar(' ', 5);

    public String soft_series6 = StringHelper.repeatChar(' ', 5);

    public String soft_series7 = StringHelper.repeatChar(' ', 5);

    public String soft_series8 = StringHelper.repeatChar(' ', 5);

    public String soft_series9 = StringHelper.repeatChar(' ', 5);

    public String soft_series10 = StringHelper.repeatChar(' ', 5);

    public String soft_series11 = StringHelper.repeatChar(' ', 5);

    public String soft_series12 = StringHelper.repeatChar(' ', 5);

    public String soft_series13 = StringHelper.repeatChar(' ', 5);

    public String soft_series14 = StringHelper.repeatChar(' ', 5);

    public String soft_series15 = StringHelper.repeatChar(' ', 5);

    public String soft_series16 = StringHelper.repeatChar(' ', 5);

    public String soft_version1 = StringHelper.repeatChar(' ', 5);

    public String soft_version2 = StringHelper.repeatChar(' ', 5);

    public String soft_version3 = StringHelper.repeatChar(' ', 5);

    public String soft_version4 = StringHelper.repeatChar(' ', 5);

    public String soft_version5 = StringHelper.repeatChar(' ', 5);

    public String soft_version6 = StringHelper.repeatChar(' ', 5);

    public String soft_version7 = StringHelper.repeatChar(' ', 5);

    public String soft_version8 = StringHelper.repeatChar(' ', 5);

    public String soft_version9 = StringHelper.repeatChar(' ', 5);

    public String soft_version10 = StringHelper.repeatChar(' ', 5);

    public String soft_version11 = StringHelper.repeatChar(' ', 5);

    public String soft_version12 = StringHelper.repeatChar(' ', 5);

    public String soft_version13 = StringHelper.repeatChar(' ', 5);

    public String soft_version14 = StringHelper.repeatChar(' ', 5);

    public String soft_version15 = StringHelper.repeatChar(' ', 5);

    public String soft_version16 = StringHelper.repeatChar(' ', 5);
    public short soft_inst;

    public String boot_ser = StringHelper.repeatChar(' ', 5);

    public String boot_ver = StringHelper.repeatChar(' ', 5);

    public String servo_ser = StringHelper.repeatChar(' ', 5);

    public String servo_ver = StringHelper.repeatChar(' ', 5);

    public String pmc_ser = StringHelper.repeatChar(' ', 5);

    public String pmc_ver = StringHelper.repeatChar(' ', 5);

    public String ladder_ser = StringHelper.repeatChar(' ', 5);

    public String ladder_ver = StringHelper.repeatChar(' ', 5);

    public String mcrlib_ser = StringHelper.repeatChar(' ', 5);

    public String mcrlib_ver = StringHelper.repeatChar(' ', 5);

    public String mcrapl_ser = StringHelper.repeatChar(' ', 5);

    public String mcrapl_ver = StringHelper.repeatChar(' ', 5);

    public String spl1_ser = StringHelper.repeatChar(' ', 5);

    public String spl1_ver = StringHelper.repeatChar(' ', 5);

    public String spl2_ser = StringHelper.repeatChar(' ', 5);

    public String spl2_ver = StringHelper.repeatChar(' ', 5);

    public String spl3_ser = StringHelper.repeatChar(' ', 5);

    public String spl3_ver = StringHelper.repeatChar(' ', 5);

    public String c_exelib_ser = StringHelper.repeatChar(' ', 5);

    public String c_exelib_ver = StringHelper.repeatChar(' ', 5);

    public String c_exeapl_ser = StringHelper.repeatChar(' ', 5);

    public String c_exeapl_ver = StringHelper.repeatChar(' ', 5);

    public String int_vga_ser = StringHelper.repeatChar(' ', 5);

    public String int_vga_ver = StringHelper.repeatChar(' ', 5);

    public String out_vga_ser = StringHelper.repeatChar(' ', 5);

    public String out_vga_ver = StringHelper.repeatChar(' ', 5);

    public String pmm_ser = StringHelper.repeatChar(' ', 5);

    public String pmm_ver = StringHelper.repeatChar(' ', 5);

    public String pmc_mng_ser = StringHelper.repeatChar(' ', 5);

    public String pmc_mng_ver = StringHelper.repeatChar(' ', 5);

    public String pmc_shin_ser = StringHelper.repeatChar(' ', 5);

    public String pmc_shin_ver = StringHelper.repeatChar(' ', 5);

    public String pmc_shout_ser = StringHelper.repeatChar(' ', 5);

    public String pmc_shout_ver = StringHelper.repeatChar(' ', 5);

    public String pmc_c_ser = StringHelper.repeatChar(' ', 5);

    public String pmc_c_ver = StringHelper.repeatChar(' ', 5);

    public String pmc_edit_ser = StringHelper.repeatChar(' ', 5);

    public String pmc_edit_ver = StringHelper.repeatChar(' ', 5);

    public String lddr_mng_ser = StringHelper.repeatChar(' ', 5);

    public String lddr_mng_ver = StringHelper.repeatChar(' ', 5);

    public String lddr_apl_ser = StringHelper.repeatChar(' ', 5);

    public String lddr_apl_ver = StringHelper.repeatChar(' ', 5);

    public String spl4_ser = StringHelper.repeatChar(' ', 5);

    public String spl4_ver = StringHelper.repeatChar(' ', 5);

    public String mcr2_ser = StringHelper.repeatChar(' ', 5);

    public String mcr2_ver = StringHelper.repeatChar(' ', 5);

    public String mcr3_ser = StringHelper.repeatChar(' ', 5);

    public String mcr3_ver = StringHelper.repeatChar(' ', 5);

    public String eth_boot_ser = StringHelper.repeatChar(' ', 5);

    public String eth_boot_ver = StringHelper.repeatChar(' ', 5);

    public byte[] reserve = new byte[5 * 8];
  }

  /* cnc_rdsyssoft2:read CNC system soft series and version (2) */
  public static class ODBSYSS2 extends JnaStructure {

    public byte[] slot_no_p = new byte[16];

    public byte[] slot_no_l = new byte[16];

    public short[] module_id = new short[16];

    public short[] soft_id = new short[16];

    public String soft_series1 = StringHelper.repeatChar(' ', 5);

    public String soft_series2 = StringHelper.repeatChar(' ', 5);

    public String soft_series3 = StringHelper.repeatChar(' ', 5);

    public String soft_series4 = StringHelper.repeatChar(' ', 5);

    public String soft_series5 = StringHelper.repeatChar(' ', 5);

    public String soft_series6 = StringHelper.repeatChar(' ', 5);

    public String soft_series7 = StringHelper.repeatChar(' ', 5);

    public String soft_series8 = StringHelper.repeatChar(' ', 5);

    public String soft_series9 = StringHelper.repeatChar(' ', 5);

    public String soft_series10 = StringHelper.repeatChar(' ', 5);

    public String soft_series11 = StringHelper.repeatChar(' ', 5);

    public String soft_series12 = StringHelper.repeatChar(' ', 5);

    public String soft_series13 = StringHelper.repeatChar(' ', 5);

    public String soft_series14 = StringHelper.repeatChar(' ', 5);

    public String soft_series15 = StringHelper.repeatChar(' ', 5);

    public String soft_series16 = StringHelper.repeatChar(' ', 5);

    public String soft_version1 = StringHelper.repeatChar(' ', 5);

    public String soft_version2 = StringHelper.repeatChar(' ', 5);

    public String soft_version3 = StringHelper.repeatChar(' ', 5);

    public String soft_version4 = StringHelper.repeatChar(' ', 5);

    public String soft_version5 = StringHelper.repeatChar(' ', 5);

    public String soft_version6 = StringHelper.repeatChar(' ', 5);

    public String soft_version7 = StringHelper.repeatChar(' ', 5);

    public String soft_version8 = StringHelper.repeatChar(' ', 5);

    public String soft_version9 = StringHelper.repeatChar(' ', 5);

    public String soft_version10 = StringHelper.repeatChar(' ', 5);

    public String soft_version11 = StringHelper.repeatChar(' ', 5);

    public String soft_version12 = StringHelper.repeatChar(' ', 5);

    public String soft_version13 = StringHelper.repeatChar(' ', 5);

    public String soft_version14 = StringHelper.repeatChar(' ', 5);

    public String soft_version15 = StringHelper.repeatChar(' ', 5);

    public String soft_version16 = StringHelper.repeatChar(' ', 5);
    public short soft_inst;

    public String boot_ser = StringHelper.repeatChar(' ', 5);

    public String boot_ver = StringHelper.repeatChar(' ', 5);

    public String servo_ser = StringHelper.repeatChar(' ', 5);

    public String servo_ver = StringHelper.repeatChar(' ', 5);

    public String pmc_ser = StringHelper.repeatChar(' ', 5);

    public String pmc_ver = StringHelper.repeatChar(' ', 5);

    public String ladder_ser = StringHelper.repeatChar(' ', 5);

    public String ladder_ver = StringHelper.repeatChar(' ', 5);

    public String mcrlib_ser = StringHelper.repeatChar(' ', 5);

    public String mcrlib_ver = StringHelper.repeatChar(' ', 5);

    public String mcrapl_ser = StringHelper.repeatChar(' ', 5);

    public String mcrapl_ver = StringHelper.repeatChar(' ', 5);

    public String spl1_ser = StringHelper.repeatChar(' ', 5);

    public String spl1_ver = StringHelper.repeatChar(' ', 5);

    public String spl2_ser = StringHelper.repeatChar(' ', 5);

    public String spl2_ver = StringHelper.repeatChar(' ', 5);

    public String spl3_ser = StringHelper.repeatChar(' ', 5);

    public String spl3_ver = StringHelper.repeatChar(' ', 5);

    public String c_exelib_ser = StringHelper.repeatChar(' ', 5);

    public String c_exelib_ver = StringHelper.repeatChar(' ', 5);

    public String c_exeapl_ser = StringHelper.repeatChar(' ', 5);

    public String c_exeapl_ver = StringHelper.repeatChar(' ', 5);

    public String int_vga_ser = StringHelper.repeatChar(' ', 5);

    public String int_vga_ver = StringHelper.repeatChar(' ', 5);

    public String out_vga_ser = StringHelper.repeatChar(' ', 5);

    public String out_vga_ver = StringHelper.repeatChar(' ', 5);

    public String pmm_ser = StringHelper.repeatChar(' ', 5);

    public String pmm_ver = StringHelper.repeatChar(' ', 5);

    public String pmc_mng_ser = StringHelper.repeatChar(' ', 5);

    public String pmc_mng_ver = StringHelper.repeatChar(' ', 5);

    public String pmc_shin_ser = StringHelper.repeatChar(' ', 5);

    public String pmc_shin_ver = StringHelper.repeatChar(' ', 5);

    public String pmc_shout_ser = StringHelper.repeatChar(' ', 5);

    public String pmc_shout_ver = StringHelper.repeatChar(' ', 5);

    public String pmc_c_ser = StringHelper.repeatChar(' ', 5);

    public String pmc_c_ver = StringHelper.repeatChar(' ', 5);

    public String pmc_edit_ser = StringHelper.repeatChar(' ', 5);

    public String pmc_edit_ver = StringHelper.repeatChar(' ', 5);

    public String lddr_mng_ser = StringHelper.repeatChar(' ', 5);

    public String lddr_mng_ver = StringHelper.repeatChar(' ', 5);

    public String lddr_apl_ser = StringHelper.repeatChar(' ', 5);

    public String lddr_apl_ver = StringHelper.repeatChar(' ', 5);

    public String spl4_ser = StringHelper.repeatChar(' ', 5);

    public String spl4_ver = StringHelper.repeatChar(' ', 5);

    public String mcr2_ser = StringHelper.repeatChar(' ', 5);

    public String mcr2_ver = StringHelper.repeatChar(' ', 5);

    public String mcr3_ser = StringHelper.repeatChar(' ', 5);

    public String mcr3_ver = StringHelper.repeatChar(' ', 5);

    public String eth_boot_ser = StringHelper.repeatChar(' ', 5);

    public String eth_boot_ver = StringHelper.repeatChar(' ', 5);

    public byte[] reserve = new byte[5 * 8];

    public String embEthe_ser = StringHelper.repeatChar(' ', 5);

    public String embEthe_ver = StringHelper.repeatChar(' ', 5);

    public byte[] reserve2 = new byte[5 * 38];
  }

  /* cnc_rdsyssoft3:read CNC system soft series and version (3) */
  public static class ODBSYSS3_data extends JnaStructure {
    public short soft_id;

    public char[] soft_series = new char[5];

    public char[] soft_edition = new char[5];
  }

  public static class ODBSYSS3 extends JnaStructure {
    public ODBSYSS3_data p1 = new ODBSYSS3_data();
    public ODBSYSS3_data p2 = new ODBSYSS3_data();
    public ODBSYSS3_data p3 = new ODBSYSS3_data();
    public ODBSYSS3_data p4 = new ODBSYSS3_data();
    public ODBSYSS3_data p5 = new ODBSYSS3_data();
    public ODBSYSS3_data p6 = new ODBSYSS3_data();
    public ODBSYSS3_data p7 = new ODBSYSS3_data();
    public ODBSYSS3_data p8 = new ODBSYSS3_data();
    public ODBSYSS3_data p9 = new ODBSYSS3_data();
    public ODBSYSS3_data p10 = new ODBSYSS3_data();
    public ODBSYSS3_data p11 = new ODBSYSS3_data();
    public ODBSYSS3_data p12 = new ODBSYSS3_data();
    public ODBSYSS3_data p13 = new ODBSYSS3_data();
    public ODBSYSS3_data p14 = new ODBSYSS3_data();
    public ODBSYSS3_data p15 = new ODBSYSS3_data();
    public ODBSYSS3_data p16 = new ODBSYSS3_data();
    public ODBSYSS3_data p17 = new ODBSYSS3_data();
    public ODBSYSS3_data p18 = new ODBSYSS3_data();
    public ODBSYSS3_data p19 = new ODBSYSS3_data();
    public ODBSYSS3_data p20 = new ODBSYSS3_data();
    public ODBSYSS3_data p21 = new ODBSYSS3_data();
    public ODBSYSS3_data p22 = new ODBSYSS3_data();
    public ODBSYSS3_data p23 = new ODBSYSS3_data();
    public ODBSYSS3_data p24 = new ODBSYSS3_data();
    public ODBSYSS3_data p25 = new ODBSYSS3_data();
    public ODBSYSS3_data p26 = new ODBSYSS3_data();
    public ODBSYSS3_data p27 = new ODBSYSS3_data();
    public ODBSYSS3_data p28 = new ODBSYSS3_data();
    public ODBSYSS3_data p29 = new ODBSYSS3_data();
    public ODBSYSS3_data p30 = new ODBSYSS3_data();
    public ODBSYSS3_data p31 = new ODBSYSS3_data();
    public ODBSYSS3_data p32 = new ODBSYSS3_data();
    public ODBSYSS3_data p33 = new ODBSYSS3_data();
    public ODBSYSS3_data p34 = new ODBSYSS3_data();
    public ODBSYSS3_data p35 = new ODBSYSS3_data();
    public ODBSYSS3_data p36 = new ODBSYSS3_data();
    public ODBSYSS3_data p37 = new ODBSYSS3_data();
    public ODBSYSS3_data p38 = new ODBSYSS3_data();
    public ODBSYSS3_data p39 = new ODBSYSS3_data();
    public ODBSYSS3_data p40 = new ODBSYSS3_data();
  }

  /* cnc_rdsyshard:read CNC system hard info */
  public static class ODBSYSH_data extends JnaStructure {

    public int id1;

    public int id2;
    public short group_id;
    public short hard_id;
    public short hard_num;
    public short slot_no;
    public short id1_format;
    public short id2_format;
  }

  public static class ODBSYSH extends JnaStructure {
    public ODBSYSH_data data1 = new ODBSYSH_data();
    public ODBSYSH_data data2 = new ODBSYSH_data();
    public ODBSYSH_data data3 = new ODBSYSH_data();
    public ODBSYSH_data data4 = new ODBSYSH_data();
    public ODBSYSH_data data5 = new ODBSYSH_data();
    public ODBSYSH_data data6 = new ODBSYSH_data();
    public ODBSYSH_data data7 = new ODBSYSH_data();
    public ODBSYSH_data data8 = new ODBSYSH_data();
    public ODBSYSH_data data9 = new ODBSYSH_data();
    public ODBSYSH_data data10 = new ODBSYSH_data();
    public ODBSYSH_data data11 = new ODBSYSH_data();
    public ODBSYSH_data data12 = new ODBSYSH_data();
    public ODBSYSH_data data13 = new ODBSYSH_data();
    public ODBSYSH_data data14 = new ODBSYSH_data();
    public ODBSYSH_data data15 = new ODBSYSH_data();
    public ODBSYSH_data data16 = new ODBSYSH_data();
    public ODBSYSH_data data17 = new ODBSYSH_data();
    public ODBSYSH_data data18 = new ODBSYSH_data();
    public ODBSYSH_data data19 = new ODBSYSH_data();
    public ODBSYSH_data data20 = new ODBSYSH_data();
    public ODBSYSH_data data21 = new ODBSYSH_data();
    public ODBSYSH_data data22 = new ODBSYSH_data();
    public ODBSYSH_data data23 = new ODBSYSH_data();
    public ODBSYSH_data data24 = new ODBSYSH_data();
    public ODBSYSH_data data25 = new ODBSYSH_data();
  }

  /* cnc_rdmdlconfig:read CNC module configuration information */
  public static class ODBMDLC extends JnaStructure {
    public short from;
    public short dram;
    public short sram;
    public short pmc;
    public short crtc;
    public short servo12;
    public short servo34;
    public short servo56;
    public short servo78;
    public short sic;
    public short pos_lsi;
    public short hi_aio;

    public short[] reserve = new short[12];
    public short drmmrc;
    public short drmarc;
    public short pmcmrc;
    public short dmaarc;
    public short iopt;
    public short hdiio;
    public short gm2gr1;
    public short crtgr2;
    public short gm1gr2;
    public short gm2gr2;
    public short cmmrb;
    public short sv5axs;
    public short sv7axs;
    public short sicaxs;
    public short posaxs;
    public short hamaxs;
    public short romr64;
    public short srmr64;
    public short dr1r64;
    public short dr2r64;
    public short iopio2;
    public short hdiio2;
    public short cmmrb2;
    public short romfap;
    public short srmfap;
    public short drmfap;
    public short drmare;
    public short pmcmre;
    public short dmaare;
    public short frmbgg;
    public short drmbgg;
    public short asrbgg;
    public short edtpsc;
    public short slcpsc;

    public short[] reserve2 = new short[32];
  }

  /* cnc_rdpscdproc:read processing condition file (processing data) */
  /* cnc_wrpscdproc:write processing condition file (processing data) */
  public static class IODBPSCD_data extends JnaStructure {
    public short slct;
    public int feed;
    public short power;
    public short freq;
    public short duty;
    public short g_press;
    public short g_kind;
    public short g_ready_t;
    public short displace;
    public int supple;
    public short edge_slt;
    public short appr_slt;

    public short[] reserve = new short[5];
  }

  public static class IODBPSCD extends JnaStructure {
    public IODBPSCD_data data1 = new IODBPSCD_data();
    public IODBPSCD_data data2 = new IODBPSCD_data();
    public IODBPSCD_data data3 = new IODBPSCD_data();
    public IODBPSCD_data data4 = new IODBPSCD_data();
    public IODBPSCD_data data5 = new IODBPSCD_data();
    public IODBPSCD_data data6 = new IODBPSCD_data();
    public IODBPSCD_data data7 = new IODBPSCD_data();
    public IODBPSCD_data data8 = new IODBPSCD_data();
    public IODBPSCD_data data9 = new IODBPSCD_data();
    public IODBPSCD_data data10 = new IODBPSCD_data();
  } // In case that the number of data is 10

  /* cnc_rdpscdpirc:read processing condition file (piercing data) */
  /* cnc_wrpscdpirc:write processing condition file (piercing data) */
  public static class IODBPIRC_data extends JnaStructure {
    public short slct;
    public short power;
    public short freq;
    public short duty;
    public short i_freq;
    public short i_duty;
    public short step_t;
    public short step_sum;
    public int pier_t;
    public short g_press;
    public short g_kind;
    public short g_time;
    public short def_pos;

    public short[] reserve = new short[4];
  }

  public static class IODBPIRC extends JnaStructure {
    public IODBPIRC_data data1 = new IODBPIRC_data();
    public IODBPIRC_data data2 = new IODBPIRC_data();
    public IODBPIRC_data data3 = new IODBPIRC_data();
  }

  /* cnc_rdpscdedge:read processing condition file (edging data) */
  /* cnc_wrpscdedge:write processing condition file (edging data) */
  public static class IODBEDGE_data extends JnaStructure {
    public short slct;
    public short angle;
    public short power;
    public short freq;
    public short duty;
    public int pier_t;
    public short g_press;
    public short g_kind;
    public int r_len;
    public short r_feed;
    public short r_freq;
    public short r_duty;

    public short[] reserve = new short[5];
  }

  public static class IODBEDGE extends JnaStructure {
    public IODBEDGE_data data1 = new IODBEDGE_data();
    public IODBEDGE_data data2 = new IODBEDGE_data();
    public IODBEDGE_data data3 = new IODBEDGE_data();
    public IODBEDGE_data data4 = new IODBEDGE_data();
    public IODBEDGE_data data5 = new IODBEDGE_data();
  }

  /* cnc_rdpscdslop:read processing condition file (slope data) */
  /* cnc_wrpscdslop:write processing condition file (slope data) */
  public static class IODBSLOP_data extends JnaStructure {
    public int slct;
    public int upleng;

    public short[] upsp = new short[10];
    public int dwleng;

    public short[] dwsp = new short[10];

    public short[] reserve = new short[10];
  }

  public static class IODBSLOP extends JnaStructure {
    public IODBSLOP_data data1 = new IODBSLOP_data();
    public IODBSLOP_data data2 = new IODBSLOP_data();
    public IODBSLOP_data data3 = new IODBSLOP_data();
    public IODBSLOP_data data4 = new IODBSLOP_data();
    public IODBSLOP_data data5 = new IODBSLOP_data();
  }

  /* cnc_rdlpwrdty:read power controll duty data */
  /* cnc_wrlpwrdty:write power controll duty data */
  public static class IODBLPWDT extends JnaStructure {
    public short slct;
    public short dty_const;
    public short dty_min;

    public short[] reserve = new short[6];
  }

  /* cnc_rdlpwrdat:read laser power data */
  public static class ODBLOPDT extends JnaStructure {
    public short slct;
    public short pwr_mon;
    public short pwr_ofs;
    public short pwr_act;
    public int feed_act;

    public short[] reserve = new short[4];
  }

  /* cnc_rdlagslt:read laser assist gas selection */
  /* cnc_wrlagslt:write laser assist gas selection */
  public static class IODBLAGSL extends JnaStructure {
    public short slct;
    public short ag_slt;
    public short agflow_slt;

    public short[] reserve = new short[6];
  }

  /* cnc_rdlagst:read laser assist gas flow */
  /* cnc_wrlagst:write laser assist gas flow */
  public static class GASFLOW extends JnaStructure {
    public short slct;
    public short pre_time;
    public short pre_press;
    public short proc_press;
    public short end_time;
    public short end_press;

    public short[] reserve = new short[3];
  }

  public static class IODBLAGST extends JnaStructure {
    public GASFLOW data1 = new GASFLOW();
    public GASFLOW data2 = new GASFLOW();
    public GASFLOW data3 = new GASFLOW();
  }

  /* cnc_rdledgprc:read laser power for edge processing */
  /* cnc_wrledgprc:write laser power for edge processing */
  public static class IODBLEGPR extends JnaStructure {
    public short slct;
    public short power;
    public short freq;
    public short duty;

    public short[] reserve = new short[5];
  }

  /* cnc_rdlprcprc:read laser power for piercing */
  /* cnc_wrlprcprc:write laser power for piercing */
  public static class IODBLPCPR extends JnaStructure {
    public short slct;
    public short power;
    public short freq;
    public short duty;
    public int time;

    public short[] reserve = new short[4];
  }

  /* cnc_rdlcmddat:read laser command data */
  public static class ODBLCMDT extends JnaStructure {
    public short slct;
    public int feed;
    public short power;
    public short freq;
    public short duty;
    public short g_kind;
    public short g_ready_t;
    public short g_press;
    public short error;
    public int dsplc;

    public short[] reserve = new short[7];
  }

  /* cnc_rdlactnum:read active number */
  public static class ODBLACTN extends JnaStructure {
    public short slct;
    public short act_proc;
    public short act_pirce;
    public short act_slop;

    public short[] reserve = new short[5];
  }

  /* cnc_rdlcmmt:read laser comment */
  public static class ODBLCMMT extends JnaStructure {

    public String comment = StringHelper.repeatChar(' ', 25);
  }

  /* cnc_rdpwofsthis:read power correction factor history data */
  public static class ODBPWOFST_data extends JnaStructure {
    public int pwratio;
    public int rfvolt;

    public short year;

    public short month;

    public short day;

    public short hour;

    public short minute;

    public short second;
  }

  public static class ODBPWOFST extends JnaStructure {
    public ODBPWOFST_data data1 = new ODBPWOFST_data();
    public ODBPWOFST_data data2 = new ODBPWOFST_data();
    public ODBPWOFST_data data3 = new ODBPWOFST_data();
    public ODBPWOFST_data data4 = new ODBPWOFST_data();
    public ODBPWOFST_data data5 = new ODBPWOFST_data();
    public ODBPWOFST_data data6 = new ODBPWOFST_data();
    public ODBPWOFST_data data7 = new ODBPWOFST_data();
    public ODBPWOFST_data data8 = new ODBPWOFST_data();
    public ODBPWOFST_data data9 = new ODBPWOFST_data();
    public ODBPWOFST_data data10 = new ODBPWOFST_data();
    public ODBPWOFST_data data11 = new ODBPWOFST_data();
    public ODBPWOFST_data data12 = new ODBPWOFST_data();
    public ODBPWOFST_data data13 = new ODBPWOFST_data();
    public ODBPWOFST_data data14 = new ODBPWOFST_data();
    public ODBPWOFST_data data15 = new ODBPWOFST_data();
    public ODBPWOFST_data data16 = new ODBPWOFST_data();
    public ODBPWOFST_data data17 = new ODBPWOFST_data();
    public ODBPWOFST_data data18 = new ODBPWOFST_data();
    public ODBPWOFST_data data19 = new ODBPWOFST_data();
    public ODBPWOFST_data data20 = new ODBPWOFST_data();
    public ODBPWOFST_data data21 = new ODBPWOFST_data();
    public ODBPWOFST_data data22 = new ODBPWOFST_data();
    public ODBPWOFST_data data23 = new ODBPWOFST_data();
    public ODBPWOFST_data data24 = new ODBPWOFST_data();
    public ODBPWOFST_data data25 = new ODBPWOFST_data();
    public ODBPWOFST_data data26 = new ODBPWOFST_data();
    public ODBPWOFST_data data27 = new ODBPWOFST_data();
    public ODBPWOFST_data data28 = new ODBPWOFST_data();
    public ODBPWOFST_data data29 = new ODBPWOFST_data();
    public ODBPWOFST_data data30 = new ODBPWOFST_data();
  }

  /* cnc_rdmngtime:read management time */
  /* cnc_wrmngtime:write management time */
  public static class IODBMNGTIME_data extends JnaStructure {

    public int life;

    public int total;
  }

  public static class IODBMNGTIME extends JnaStructure {
    public IODBMNGTIME_data data1 = new IODBMNGTIME_data();
    public IODBMNGTIME_data data2 = new IODBMNGTIME_data();
    public IODBMNGTIME_data data3 = new IODBMNGTIME_data();
    public IODBMNGTIME_data data4 = new IODBMNGTIME_data();
    public IODBMNGTIME_data data5 = new IODBMNGTIME_data();
    public IODBMNGTIME_data data6 = new IODBMNGTIME_data();
    public IODBMNGTIME_data data7 = new IODBMNGTIME_data();
    public IODBMNGTIME_data data8 = new IODBMNGTIME_data();
    public IODBMNGTIME_data data9 = new IODBMNGTIME_data();
    public IODBMNGTIME_data data10 = new IODBMNGTIME_data();
  } // In case that the number of data is 10

  /* cnc_rddischarge:read data related to electrical discharge at power correction ends */
  public static class ODBDISCHRG extends JnaStructure {

    public short aps;

    public short year;

    public short month;

    public short day;

    public short hour;

    public short minute;

    public short second;
    public short hpc;
    public short hfq;
    public short hdt;
    public short hpa;
    public int hce;

    public int[] rfi = new int[8];

    public int[] rfv = new int[8];

    public int[] dci = new int[8];

    public int[] dcv = new int[8];

    public int[] dcw = new int[8];
  }

  /* cnc_rddischrgalm:read alarm history data related to electrical discharg */
  public static class ODBDISCHRGALM_data extends JnaStructure {

    public short year;

    public short month;

    public short day;

    public short hour;

    public short minute;

    public short second;
    public int almnum;

    public int psec;
    public short hpc;
    public short hfq;
    public short hdt;
    public short hpa;
    public int hce;

    public short asq;

    public short psu;

    public short aps;
    public short dummy;

    public int[] rfi = new int[8];

    public int[] rfv = new int[8];

    public int[] dci = new int[8];

    public int[] dcv = new int[8];

    public int[] dcw = new int[8];

    public short[] almcd = new short[8];
  }

  public static class ODBDISCHRGALM extends JnaStructure {
    public ODBDISCHRGALM_data data1 = new ODBDISCHRGALM_data();
    public ODBDISCHRGALM_data data2 = new ODBDISCHRGALM_data();
    public ODBDISCHRGALM_data data3 = new ODBDISCHRGALM_data();
    public ODBDISCHRGALM_data data4 = new ODBDISCHRGALM_data();
    public ODBDISCHRGALM_data data5 = new ODBDISCHRGALM_data();
  }

  /* cnc_gettimer:get date and time from cnc */
  /* cnc_settimer:set date and time for cnc */
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
  public final static class TIMER_DATE extends JnaStructure {
    public short year;
    public short month;
    public short date;

    public TIMER_DATE clone() {
      TIMER_DATE varCopy = new TIMER_DATE();
      varCopy.year = this.year;
      varCopy.month = this.month;
      varCopy.date = this.date;
      return varCopy;
    }
  }

  //C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
  public final static class TIMER_TIME extends JnaStructure {
    public short hour;
    public short minute;
    public short second;

    public TIMER_TIME clone() {
      TIMER_TIME varCopy = new TIMER_TIME();
      varCopy.hour = this.hour;
      varCopy.minute = this.minute;
      varCopy.second = this.second;
      return varCopy;
    }
  }

  public static class IODBTIMER extends JnaStructure {
    public short type;
    public short dummy;
    public TIMER_DATE date = new TIMER_DATE();
    public TIMER_TIME time = new TIMER_TIME();
  }

  /* cnc_rdtimer:read timer data from cnc */
  /* cnc_wrtimer:write timer data for cnc */
  public static class IODBTIME extends JnaStructure {
    public int minute;
    public int msec;
  }

  /* cnc_rdtlctldata: read tool controll data */
  /* cnc_wrtlctldata: write tool controll data */
  public static class IODBTLCTL extends JnaStructure {
    public short slct;
    public short used_tool;
    public short turret_indx;
    public int zero_tl_no;
    public int t_axis_move;

    public int[] total_punch = new int[2];

    public short[] reserve = new short[11];
  }

  /* cnc_rdtooldata: read tool data */
  /* cnc_wrtooldata: read tool data */
  public static class IODBTLDT_data extends JnaStructure {
    public short slct;
    public int tool_no;
    public int x_axis_ofs;
    public int y_axis_ofs;
    public int turret_pos;
    public int chg_tl_no;
    public int punch_count;
    public int tool_life;
    public int m_tl_radius;
    public int m_tl_angle;

    public byte tl_shape;
    public int tl_size_i;
    public int tl_size_j;
    public int tl_angle;

    public int[] reserve = new int[3];
  }

  public static class IODBTLDT extends JnaStructure {
    public IODBTLDT_data data1 = new IODBTLDT_data();
    public IODBTLDT_data data2 = new IODBTLDT_data();
    public IODBTLDT_data data3 = new IODBTLDT_data();
    public IODBTLDT_data data4 = new IODBTLDT_data();
    public IODBTLDT_data data5 = new IODBTLDT_data();
    public IODBTLDT_data data6 = new IODBTLDT_data();
    public IODBTLDT_data data7 = new IODBTLDT_data();
    public IODBTLDT_data data8 = new IODBTLDT_data();
    public IODBTLDT_data data9 = new IODBTLDT_data();
    public IODBTLDT_data data10 = new IODBTLDT_data();
  } // In case that the number of data is 10

  /* cnc_rdmultitldt: read multi tool data */
  /* cnc_wrmultitldt: write multi tool data */
  public static class IODBMLTTL_data extends JnaStructure {
    public short slct;
    public short m_tl_no;
    public int m_tl_radius;
    public int m_tl_angle;
    public int x_axis_ofs;
    public int y_axis_ofs;

    public byte tl_shape;
    public int tl_size_i;
    public int tl_size_j;
    public int tl_angle;

    public int[] reserve = new int[7];
  }

  public static class IODBMLTTL extends JnaStructure {
    public IODBMLTTL_data data1 = new IODBMLTTL_data();
    public IODBMLTTL_data data2 = new IODBMLTTL_data();
    public IODBMLTTL_data data3 = new IODBMLTTL_data();
    public IODBMLTTL_data data4 = new IODBMLTTL_data();
    public IODBMLTTL_data data5 = new IODBMLTTL_data();
    public IODBMLTTL_data data6 = new IODBMLTTL_data();
    public IODBMLTTL_data data7 = new IODBMLTTL_data();
    public IODBMLTTL_data data8 = new IODBMLTTL_data();
    public IODBMLTTL_data data9 = new IODBMLTTL_data();
    public IODBMLTTL_data data10 = new IODBMLTTL_data();
  } // In case that the number of data is 10

  /* cnc_rdmtapdata: read multi tap data */
  /* cnc_wrmtapdata: write multi tap data */
  public static class IODBMTAP_data extends JnaStructure {
    public short slct;
    public int tool_no;
    public int x_axis_ofs;
    public int y_axis_ofs;
    public int punch_count;
    public int tool_life;

    public int[] reserve = new int[11];
  }

  public static class IODBMTAP extends JnaStructure {
    public IODBMTAP_data data1 = new IODBMTAP_data();
    public IODBMTAP_data data2 = new IODBMTAP_data();
    public IODBMTAP_data data3 = new IODBMTAP_data();
    public IODBMTAP_data data4 = new IODBMTAP_data();
    public IODBMTAP_data data5 = new IODBMTAP_data();
    public IODBMTAP_data data6 = new IODBMTAP_data();
    public IODBMTAP_data data7 = new IODBMTAP_data();
    public IODBMTAP_data data8 = new IODBMTAP_data();
    public IODBMTAP_data data9 = new IODBMTAP_data();
    public IODBMTAP_data data10 = new IODBMTAP_data();
  }

  /* cnc_rdtoolinfo: read tool information */
  public static class ODBPTLINF extends JnaStructure {
    public short tld_max;
    public short mlt_max;
    public short reserve;

    public short[] tld_size = new short[16];

    public short[] mlt_size = new short[16];

    public short[] reserves = new short[16];
  }

  /* cnc_rdsafetyzone: read safetyzone data */
  /* cnc_wrsafetyzone: write safetyzone data */
  public static class IODBSAFE_data extends JnaStructure {
    public short slct;

    public int[] data = new int[3];
  }

  public static class IODBSAFE extends JnaStructure {
    public IODBSAFE_data data1 = new IODBSAFE_data();
    public IODBSAFE_data data2 = new IODBSAFE_data();
    public IODBSAFE_data data3 = new IODBSAFE_data();
    public IODBSAFE_data data4 = new IODBSAFE_data();
  } // In case that the number of data is 4

  /* cnc_rdtoolzone: read toolzone data */
  /* cnc_wrtoolzone: write toolzone data */
  public static class IODBTLZN_data extends JnaStructure {
    public short slct;

    public int[] data = new int[12];
  }

  public static class IODBTLZN extends JnaStructure {
    public IODBTLZN_data data1 = new IODBTLZN_data();
    public IODBTLZN_data data2 = new IODBTLZN_data();
    public IODBTLZN_data data3 = new IODBTLZN_data();
    public IODBTLZN_data data4 = new IODBTLZN_data();
    public IODBTLZN_data data5 = new IODBTLZN_data();
    public IODBTLZN_data data6 = new IODBTLZN_data();
    public IODBTLZN_data data7 = new IODBTLZN_data();
    public IODBTLZN_data data8 = new IODBTLZN_data();
    public IODBTLZN_data data9 = new IODBTLZN_data();
    public IODBTLZN_data data10 = new IODBTLZN_data();
    public IODBTLZN_data data11 = new IODBTLZN_data();
    public IODBTLZN_data data12 = new IODBTLZN_data();
  } // In case that the number of data is 12

  /* cnc_rdacttlzone: read active toolzone data */
  public static class ODBACTTLZN extends JnaStructure {
    public short act_no;

    public int[] data = new int[2];
  }

  /* cnc_rdbrstrinfo:read block restart information */
  public static class ODBBRS extends JnaStructure {

    public int[] dest = new int[MAX_AXIS];

    public int[] dist = new int[MAX_AXIS];
  } //  In case that the number of axes is MAX_AXIS

  /* cnc_rdradofs:read tool radius offset for position data */
  public static class ODBROFS extends JnaStructure {
    public short mode;

    public short[] pln_axes = new short[2];

    public int[] ofsvct = new int[2];
  }

  /* cnc_rdlenofs:read tool length offset for position data */
  public static class ODBLOFS extends JnaStructure {
    public short mode;

    public int[] ofsvct;
  } //  In case that the number of axes is MAX_AXIS

  /* cnc_rdfixcycle:read fixed cycle for position data */
  public static class ODBFIX extends JnaStructure {
    public short mode;

    public short[] pln_axes = new short[2];
    public short drl_axes;
    public int i_pos;
    public int r_pos;
    public int z_pos;
    public int cmd_cnt;
    public int act_cnt;
    public int cut;

    public int[] shift = new int[2];
  }

  /* cnc_rdcdrotate:read coordinate rotate for position data */
  public static class ODBROT extends JnaStructure {
    public short mode;

    public short[] pln_axes = new short[2];

    public int[] center = new int[2];
    public int angle;
  }

  /* cnc_rd3dcdcnv:read 3D coordinate convert for position data */
  public static class ODB3DCD extends JnaStructure {
    public short mode;
    public short dno;

    public short[] cd_axes = new short[3];

    public int[] center = new int[2 * 3];

    public int[] direct = new int[2 * 3];

    public int[] angle = new int[2];
  }

  /* cnc_rdmirimage:read programable mirror image for position data */
  public static class ODBMIR extends JnaStructure {
    public short mode;
    public int mir_flag;

    public int[] mir_pos = new int[MAX_AXIS];
  } //  In case that the number of axes is MAX_AXIS

  /* cnc_rdscaling:read scaling data for position data */
  public static class ODBSCL extends JnaStructure {
    public short mode;

    public int[] center = new int[MAX_AXIS];

    public int[] magnif = new int[MAX_AXIS];
  } //  In case that the number of axes is MAX_AXIS

  /* cnc_rd3dtofs:read 3D tool offset for position data */
  public static class ODB3DTO extends JnaStructure {
    public short mode;

    public short[] ofs_axes = new short[3];

    public int[] ofsvct = new int[3];
  }

  /* cnc_rdposofs:read tool position offset for position data */
  public static class ODBPOFS extends JnaStructure {
    public short mode;

    public int[] ofsvct = new int[MAX_AXIS];
  } //  In case that the number of axes is MAX_AXIS

  /* cnc_rdhpccset:read hpcc setting data */
  /* cnc_wrhpccset:write hpcc setting data */
  public static class IODBHPST extends JnaStructure {
    public short slct;
    public short hpcc;
    public short multi;
    public short ovr1;
    public short ign_f;
    public short foward;
    public int max_f;
    public short ovr2;
    public short ovr3;
    public short ovr4;

    public int[] reserve = new int[7];
  }

  /* cnc_rdhpcctupr:read hpcc tuning data ( parameter input ) */
  /* cnc_wrhpcctupr:write hpcc tuning data ( parameter input ) */
  public static class IODBHPPR_tune extends JnaStructure {
    public short slct;
    public short diff;
    public short fine;
    public short acc_lv;
    public int max_f;
    public short bipl;
    public short aipl;
    public int corner;
    public short clamp;
    public int radius;
    public int max_cf;
    public int min_cf;
    public int foward;

    public int[] reserve = new int[5];
  }

  public static class IODBHPPR extends JnaStructure {
    public IODBHPPR_tune tune1 = new IODBHPPR_tune();
    public IODBHPPR_tune tune2 = new IODBHPPR_tune();
    public IODBHPPR_tune tune3 = new IODBHPPR_tune();
  }

  /* cnc_rdhpcctuac:read hpcc tuning data ( acc input ) */
  /* cnc_wrhpcctuac:write hpcc tuning data ( acc input ) */
  public static class IODBHPAC_tune extends JnaStructure {
    public short slct;
    public short diff;
    public short fine;
    public short acc_lv;
    public int bipl;
    public short aipl;
    public int corner;
    public int clamp;
    public int c_acc;
    public int foward;

    public int[] reserve = new int[8];
  }

  public static class IODBHPAC extends JnaStructure {
    public IODBHPAC_tune tune1 = new IODBHPAC_tune();
    public IODBHPAC_tune tune2 = new IODBHPAC_tune();
    public IODBHPAC_tune tune3 = new IODBHPAC_tune();
  }

  /* cnc_rd3dtooltip:read tip of tool for 3D handle */
  /* cnc_rd3dmovrlap:read move overrlap of tool for 3D handle */
  public static class ODB3DHDL_data extends JnaStructure {

    public short[] axes = new short[5];

    public int[] data = new int[5];
  }

  public static class ODB3DHDL extends JnaStructure {
    public ODB3DHDL_data data1 = new ODB3DHDL_data();
    public ODB3DHDL_data data2 = new ODB3DHDL_data();
  }

  /* cnc_rd3dpulse:read pulse for 3D handle */
  public static class ODB3DPLS_data extends JnaStructure {
    public int right_angle_x;
    public int right_angle_y;
    public int tool_axis;
    public int tool_tip_a_b;
    public int tool_tip_c;
  }

  public static class ODB3DPLS extends JnaStructure {
    public ODB3DPLS_data pls1 = new ODB3DPLS_data();
    public ODB3DPLS_data pls2 = new ODB3DPLS_data();
  }

  /* cnc_rdaxisname: read axis name */
  public static class ODBAXISNAME_data extends JnaStructure {

    public byte name; // axis name

    public byte suff; // suffix
  }

  //#if M_AXIS2
  public static class ODBAXISNAME extends JnaStructure {
    public ODBAXISNAME_data data1 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data2 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data3 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data4 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data5 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data6 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data7 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data8 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data9 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data10 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data11 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data12 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data13 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data14 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data15 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data16 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data17 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data18 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data19 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data20 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data21 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data22 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data23 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data24 = new ODBAXISNAME_data();
  }

  //#elif FS15D
  /*public static class ODBAXISNAME
  {
    public ODBAXISNAME_data data1 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data2 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data3 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data4 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data5 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data6 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data7 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data8 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data9 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data10 = new ODBAXISNAME_data();
  }*/
  //#else
  /*public static class ODBAXISNAME
  {
    public ODBAXISNAME_data data1 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data2 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data3 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data4 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data5 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data6 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data7 = new ODBAXISNAME_data();
    public ODBAXISNAME_data data8 = new ODBAXISNAME_data();
  }*/
//#endif
  /* cnc_rdspdlname: read spindle name */
  public static class ODBSPDLNAME_data extends JnaStructure {

    public byte name; // spindle name

    public byte suff1; // suffix

    public byte suff2; // suffix

    public byte suff3; // suffix
  }

  public static class ODBSPDLNAME extends JnaStructure {
    public ODBSPDLNAME_data data1 = new ODBSPDLNAME_data();
    public ODBSPDLNAME_data data2 = new ODBSPDLNAME_data();
    public ODBSPDLNAME_data data3 = new ODBSPDLNAME_data();
    public ODBSPDLNAME_data data4 = new ODBSPDLNAME_data();


  }

  /* cnc_exaxisname: read extended axis name */
  public static class ODBEXAXISNAME extends JnaStructure {

    public String axname1 = StringHelper.repeatChar(' ', 4);

    public String axname2 = StringHelper.repeatChar(' ', 4);

    public String axname3 = StringHelper.repeatChar(' ', 4);

    public String axname4 = StringHelper.repeatChar(' ', 4);

    public String axname5 = StringHelper.repeatChar(' ', 4);

    public String axname6 = StringHelper.repeatChar(' ', 4);

    public String axname7 = StringHelper.repeatChar(' ', 4);

    public String axname8 = StringHelper.repeatChar(' ', 4);

    public String axname9 = StringHelper.repeatChar(' ', 4);

    public String axname10 = StringHelper.repeatChar(' ', 4);

    public String axname11 = StringHelper.repeatChar(' ', 4);

    public String axname12 = StringHelper.repeatChar(' ', 4);

    public String axname13 = StringHelper.repeatChar(' ', 4);

    public String axname14 = StringHelper.repeatChar(' ', 4);

    public String axname15 = StringHelper.repeatChar(' ', 4);

    public String axname16 = StringHelper.repeatChar(' ', 4);

    public String axname17 = StringHelper.repeatChar(' ', 4);

    public String axname18 = StringHelper.repeatChar(' ', 4);

    public String axname19 = StringHelper.repeatChar(' ', 4);

    public String axname20 = StringHelper.repeatChar(' ', 4);

    public String axname21 = StringHelper.repeatChar(' ', 4);

    public String axname22 = StringHelper.repeatChar(' ', 4);

    public String axname23 = StringHelper.repeatChar(' ', 4);

    public String axname24 = StringHelper.repeatChar(' ', 4);

    public String axname25 = StringHelper.repeatChar(' ', 4);

    public String axname26 = StringHelper.repeatChar(' ', 4);

    public String axname27 = StringHelper.repeatChar(' ', 4);

    public String axname28 = StringHelper.repeatChar(' ', 4);

    public String axname29 = StringHelper.repeatChar(' ', 4);

    public String axname30 = StringHelper.repeatChar(' ', 4);

    public String axname31 = StringHelper.repeatChar(' ', 4);

    public String axname32 = StringHelper.repeatChar(' ', 4);
  }

  /* cnc_wrunsolicprm: Set the unsolicited message parameters */
  /* cnc_rdunsolicprm: Get the unsolicited message parameters */
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
  public final static class IODBUNSOLIC_pmc extends JnaStructure {
    public short type;
    public short rdaddr;
    public short rdno;
    public short rdsize;
    private int dummy;

    public IODBUNSOLIC_pmc clone() {
      IODBUNSOLIC_pmc varCopy = new IODBUNSOLIC_pmc();
      varCopy.type = this.type;
      varCopy.rdaddr = this.rdaddr;
      varCopy.rdno = this.rdno;
      varCopy.rdsize = this.rdsize;
      varCopy.dummy = this.dummy;
      return varCopy;
    }
  }

  public static class IODBUNSOLIC extends JnaStructure {

    public String ipaddr = StringHelper.repeatChar(' ', 16);

    public short port;
    public short reqaddr;
    public short pmcno;
    public short retry;
    public short timeout;
    public short alivetime;
    public short setno;

    public IODBUNSOLIC_pmc[] rddata = new IODBUNSOLIC_pmc[3];
  }

  /* cnc_rdunsolicmsg: Reads the unsolicited message data */
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
  public final static class IDBUNSOLICMSG_msg extends JnaStructure {
    public short rdsize;
    public IntByReference data = new IntByReference();

    public IDBUNSOLICMSG_msg clone() {
      IDBUNSOLICMSG_msg varCopy = new IDBUNSOLICMSG_msg();
      varCopy.rdsize = this.rdsize;
      varCopy.data = this.data;
      return varCopy;
    }
  }

  public static class IDBUNSOLICMSG extends JnaStructure {
    public short getno;

    public IDBUNSOLICMSG_msg[] msg = new IDBUNSOLICMSG_msg[3];
  }

  /* cnc_rdpm_cncitem: read cnc maintenance item */
  /* cnc_rdpm_mcnitem: read machine specific maintenance item */
  /* cnc_wrpm_mcnitem: write machine specific maintenance item */
  public static class IODBITEM extends JnaStructure {

    public String name1 = StringHelper.repeatChar(' ', 62);

    public String name2 = StringHelper.repeatChar(' ', 62);

    public String name3 = StringHelper.repeatChar(' ', 62);

    public String name4 = StringHelper.repeatChar(' ', 62);

    public String name5 = StringHelper.repeatChar(' ', 62);

    public String name6 = StringHelper.repeatChar(' ', 62);

    public String name7 = StringHelper.repeatChar(' ', 62);

    public String name8 = StringHelper.repeatChar(' ', 62);

    public String name9 = StringHelper.repeatChar(' ', 62);

    public String name10 = StringHelper.repeatChar(' ', 62);
  }

  /* cnc_rdpm_item:read maintenance item status */
  public static class IODBPMAINTE_data extends JnaStructure {

    public String name = StringHelper.repeatChar(' ', 62); // name
    public int type; // life count type
    public int total; // total life time (minite basis)
    public int remain; // life rest time
    public int stat; // life state
  }

  public static class IODBPMAINTE extends JnaStructure {
    public IODBPMAINTE_data data1 = new IODBPMAINTE_data();
    public IODBPMAINTE_data data2 = new IODBPMAINTE_data();
    public IODBPMAINTE_data data3 = new IODBPMAINTE_data();
    public IODBPMAINTE_data data4 = new IODBPMAINTE_data();
    public IODBPMAINTE_data data5 = new IODBPMAINTE_data();
    public IODBPMAINTE_data data6 = new IODBPMAINTE_data();
    public IODBPMAINTE_data data7 = new IODBPMAINTE_data();
    public IODBPMAINTE_data data8 = new IODBPMAINTE_data();
    public IODBPMAINTE_data data9 = new IODBPMAINTE_data();
    public IODBPMAINTE_data data10 = new IODBPMAINTE_data();
  }

  /* cnc_sysinfo_ex:read CNC system path information */
  public static class ODBSYSEX_path extends JnaStructure {
    public short system;
    public short group;
    public short attrib;
    public short ctrl_axis;
    public short ctrl_srvo;
    public short ctrl_spdl;
    public short mchn_no;
    public short reserved;
  }

  public static class ODBSYSEX_data extends JnaStructure {
    public ODBSYSEX_path data1 = new ODBSYSEX_path();
    public ODBSYSEX_path data2 = new ODBSYSEX_path();
    public ODBSYSEX_path data3 = new ODBSYSEX_path();
    public ODBSYSEX_path data4 = new ODBSYSEX_path();
    public ODBSYSEX_path data5 = new ODBSYSEX_path();
    public ODBSYSEX_path data6 = new ODBSYSEX_path();
    public ODBSYSEX_path data7 = new ODBSYSEX_path();
    public ODBSYSEX_path data8 = new ODBSYSEX_path();
    public ODBSYSEX_path data9 = new ODBSYSEX_path();
    public ODBSYSEX_path data10 = new ODBSYSEX_path();
  }

  public static class ODBSYSEX extends JnaStructure {
    public short max_axis;
    public short max_spdl;
    public short max_path;
    public short max_mchn;
    public short ctrl_axis;
    public short ctrl_srvo;
    public short ctrl_spdl;
    public short ctrl_path;
    public short ctrl_mchn;

    public short[] reserved = new short[3];
    public ODBSYSEX_data path = new ODBSYSEX_data();
  }

  /*------------------*/
  /* CNC : SERCOS I/F */
  /*------------------*/
  /* cnc_srcsrdidinfo:Read ID information of SERCOS I/F */
  /* cnc_srcswridinfo:Write ID information of SERCOS I/F */
  public static class IODBIDINF extends JnaStructure {
    public int id_no;
    public short drv_no;
    public short acc_element;
    public short err_general;
    public short err_id_no;
    public short err_id_name;
    public short err_attr;
    public short err_unit;
    public short err_min_val;
    public short err_max_val;
    public short id_name_len;
    public short id_name_max;

    public String id_name = StringHelper.repeatChar(' ', 60);
    public int attr;
    public short unit_len;
    public short unit_max;

    public byte[] unit = new byte[12];
    public int min_val;
    public int max_val;
  }

  /* cnc_srcsrdexstat:Get execution status of reading/writing operation data of SERCOS I/F */
  public static class ODBSRCSST extends JnaStructure {
    public short acc_element;
    public short err_general;
    public short err_id_no;
    public short err_attr;
    public short err_op_data;
  }

  /* cnc_srcsrdlayout:Read drive assign of SERCOS I/F */
  public static class ODBSRCSLYT extends JnaStructure {

    public short[] spndl = new short[4];

    public short[] servo = new short[8];

    public String axis_name = StringHelper.repeatChar(' ', 8);
  }

  /*----------------------------*/
  /* CNC : Servo Guide          */
  /*----------------------------*/
  /* cnc_sdsetchnl:Servo Guide (Channel data set) */
  public static class IDBCHAN_data extends JnaStructure {

    public byte chno;
    public byte axis;
    public int datanum;

    public short datainf;
    public short dataadr;
  }

  public static class IDBCHAN extends JnaStructure {
    public IDBCHAN_data data1 = new IDBCHAN_data();
    public IDBCHAN_data data2 = new IDBCHAN_data();
    public IDBCHAN_data data3 = new IDBCHAN_data();
    public IDBCHAN_data data4 = new IDBCHAN_data();
    public IDBCHAN_data data5 = new IDBCHAN_data();
    public IDBCHAN_data data6 = new IDBCHAN_data();
    public IDBCHAN_data data7 = new IDBCHAN_data();
    public IDBCHAN_data data8 = new IDBCHAN_data();
  }

  /* cnc_sdsetchnl:Servo Guide (read Sampling data) */
  /* cnc_sfbreadsmpl:Servo feedback data (read Sampling data) */
  public static class ODBSD extends JnaStructure {
    public IntByReference chadata = new IntByReference();
    public IntByReference count = new IntByReference();
    //com.sun.jna.ptr.IntByReference
  }

  /* cnc_sfbsetchnl:Servo feedback data (Channel data set) */
  public static class IDBSFBCHAN extends JnaStructure {

    public byte chno;
    public byte axis;

    public short shift;
  }

  /*-------------------------*/
  /* CNC : FS18-LN function  */
  /*-------------------------*/
  /* cnc_allowcnd:read allowanced state */
  public static class ODBCAXIS extends JnaStructure {
    public short dummy; // dummy
    public short type; // axis number

    public byte[] data = new byte[MAX_AXIS]; // data value
  }

  /*---------------------------------*/
  /* CNC : C-EXE SRAM file function  */
  /*---------------------------------*/
  /* read C-EXE SRAM disk directory */
  public static class CFILEINFO_data extends JnaStructure {

    public String fname = StringHelper.repeatChar(' ', 12); // file name
    public int file_size; // file size (bytes)
    public int file_attr; // attribute
    public short year; // year
    public short month; // month
    public short day; // day
    public short hour; // hour
    public short minute; // mimute
    public short second; // second
  }

  public static class CFILEINFO extends JnaStructure {
    public CFILEINFO_data data1 = new CFILEINFO_data();
    public CFILEINFO_data data2 = new CFILEINFO_data();
    public CFILEINFO_data data3 = new CFILEINFO_data();
    public CFILEINFO_data data4 = new CFILEINFO_data();
    public CFILEINFO_data data5 = new CFILEINFO_data();
    public CFILEINFO_data data6 = new CFILEINFO_data();
    public CFILEINFO_data data7 = new CFILEINFO_data();
    public CFILEINFO_data data8 = new CFILEINFO_data();
    public CFILEINFO_data data9 = new CFILEINFO_data();
    public CFILEINFO_data data10 = new CFILEINFO_data();
  }

  /*-----*/
  /* PMC */
  /*-----*/
  /* pmc_rdpmcrng:read PMC data(area specified) */
  /* pmc_wrpmcrng:write PMC data(area specified) */
  public static class IODBPMC0 extends JnaStructure {

    public short type_a; // PMC address type

    public short type_d; // PMC data type

    public short datano_s; // start PMC address

    public short datano_e; // end PMC address

    public byte[] cdata = new byte[5]; // PMC data
  } // In case that the number of data is 5

  public static class IODBPMC1 extends JnaStructure {

    public short type_a; // PMC address type

    public short type_d; // PMC data type

    public short datano_s; // start PMC address

    public short datano_e; // end PMC address

    public short[] idata = new short[5];
  } // In case that the number of data is 5

  public static class IODBPMC2 extends JnaStructure {

    public short type_a; // PMC address type

    public short type_d; // PMC data type

    public short datano_s; // start PMC address

    public short datano_e; // end PMC address

    public int[] ldata = new int[5];
  } // In case that the number of data is 5

  /* pmc_rdpmcinfo:read informations of PMC data */
  public static class ODBPMCINF_info extends JnaStructure {
    public char pmc_adr;

    public byte adr_attr;

    public short top_num;

    public short last_num;
  }

  public static class ODBPMCINF1 extends JnaStructure {
    public ODBPMCINF_info info1 = new ODBPMCINF_info();
    public ODBPMCINF_info info2 = new ODBPMCINF_info();
    public ODBPMCINF_info info3 = new ODBPMCINF_info();
    public ODBPMCINF_info info4 = new ODBPMCINF_info();
    public ODBPMCINF_info info5 = new ODBPMCINF_info();
    public ODBPMCINF_info info6 = new ODBPMCINF_info();
    public ODBPMCINF_info info7 = new ODBPMCINF_info();
    public ODBPMCINF_info info8 = new ODBPMCINF_info();
    public ODBPMCINF_info info9 = new ODBPMCINF_info();
    public ODBPMCINF_info info10 = new ODBPMCINF_info();
    public ODBPMCINF_info info11 = new ODBPMCINF_info();
    public ODBPMCINF_info info12 = new ODBPMCINF_info();
    public ODBPMCINF_info info13 = new ODBPMCINF_info();
    public ODBPMCINF_info info14 = new ODBPMCINF_info();
    public ODBPMCINF_info info15 = new ODBPMCINF_info();
    public ODBPMCINF_info info16 = new ODBPMCINF_info();
    public ODBPMCINF_info info17 = new ODBPMCINF_info();
    public ODBPMCINF_info info18 = new ODBPMCINF_info();
    public ODBPMCINF_info info19 = new ODBPMCINF_info();
    public ODBPMCINF_info info20 = new ODBPMCINF_info();
    public ODBPMCINF_info info21 = new ODBPMCINF_info();
    public ODBPMCINF_info info22 = new ODBPMCINF_info();
    public ODBPMCINF_info info23 = new ODBPMCINF_info();
    public ODBPMCINF_info info24 = new ODBPMCINF_info();
    public ODBPMCINF_info info25 = new ODBPMCINF_info();
    public ODBPMCINF_info info26 = new ODBPMCINF_info();
    public ODBPMCINF_info info27 = new ODBPMCINF_info();
    public ODBPMCINF_info info28 = new ODBPMCINF_info();
    public ODBPMCINF_info info29 = new ODBPMCINF_info();
    public ODBPMCINF_info info30 = new ODBPMCINF_info();
    public ODBPMCINF_info info31 = new ODBPMCINF_info();
    public ODBPMCINF_info info32 = new ODBPMCINF_info();
    public ODBPMCINF_info info33 = new ODBPMCINF_info();
    public ODBPMCINF_info info34 = new ODBPMCINF_info();
    public ODBPMCINF_info info35 = new ODBPMCINF_info();
    public ODBPMCINF_info info36 = new ODBPMCINF_info();
    public ODBPMCINF_info info37 = new ODBPMCINF_info();
    public ODBPMCINF_info info38 = new ODBPMCINF_info();
    public ODBPMCINF_info info39 = new ODBPMCINF_info();
    public ODBPMCINF_info info40 = new ODBPMCINF_info();
    public ODBPMCINF_info info41 = new ODBPMCINF_info();
    public ODBPMCINF_info info42 = new ODBPMCINF_info();
    public ODBPMCINF_info info43 = new ODBPMCINF_info();
    public ODBPMCINF_info info44 = new ODBPMCINF_info();
    public ODBPMCINF_info info45 = new ODBPMCINF_info();
    public ODBPMCINF_info info46 = new ODBPMCINF_info();
    public ODBPMCINF_info info47 = new ODBPMCINF_info();
    public ODBPMCINF_info info48 = new ODBPMCINF_info();
    public ODBPMCINF_info info49 = new ODBPMCINF_info();
    public ODBPMCINF_info info50 = new ODBPMCINF_info();
    public ODBPMCINF_info info51 = new ODBPMCINF_info();
    public ODBPMCINF_info info52 = new ODBPMCINF_info();
    public ODBPMCINF_info info53 = new ODBPMCINF_info();
    public ODBPMCINF_info info54 = new ODBPMCINF_info();
    public ODBPMCINF_info info55 = new ODBPMCINF_info();
    public ODBPMCINF_info info56 = new ODBPMCINF_info();
    public ODBPMCINF_info info57 = new ODBPMCINF_info();
    public ODBPMCINF_info info58 = new ODBPMCINF_info();
    public ODBPMCINF_info info59 = new ODBPMCINF_info();
    public ODBPMCINF_info info60 = new ODBPMCINF_info();
    public ODBPMCINF_info info61 = new ODBPMCINF_info();
    public ODBPMCINF_info info62 = new ODBPMCINF_info();
    public ODBPMCINF_info info63 = new ODBPMCINF_info();
    public ODBPMCINF_info info64 = new ODBPMCINF_info();
  }

  public static class ODBPMCINF extends JnaStructure {
    public short datano;
    public ODBPMCINF1 info = new ODBPMCINF1();
  }

  /* pmc_rdcntldata:read PMC parameter data table control data */
  /* pmc_wrcntldata:write PMC parameter data table control data */
  public static class IODBPMCCNTL_info extends JnaStructure {

    public byte tbl_prm;

    public byte data_type;

    public short data_size;

    public short data_dsp;
    public short dummy;
  }

  public static class IODBPMCCNTL1 extends JnaStructure {
    public IODBPMCCNTL_info info1 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info2 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info3 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info4 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info5 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info6 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info7 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info8 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info9 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info10 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info11 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info12 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info13 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info14 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info15 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info16 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info17 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info18 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info19 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info20 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info21 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info22 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info23 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info24 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info25 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info26 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info27 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info28 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info29 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info30 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info31 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info32 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info33 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info34 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info35 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info36 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info37 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info38 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info39 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info40 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info41 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info42 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info43 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info44 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info45 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info46 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info47 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info48 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info49 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info50 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info51 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info52 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info53 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info54 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info55 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info56 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info57 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info58 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info59 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info60 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info61 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info62 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info63 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info64 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info65 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info66 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info67 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info68 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info69 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info70 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info71 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info72 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info73 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info74 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info75 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info76 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info77 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info78 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info79 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info80 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info81 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info82 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info83 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info84 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info85 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info86 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info87 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info88 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info89 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info90 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info91 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info92 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info93 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info94 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info95 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info96 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info97 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info98 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info99 = new IODBPMCCNTL_info();
    public IODBPMCCNTL_info info100 = new IODBPMCCNTL_info();
  }

  public static class IODBPMCCNTL extends JnaStructure {
    public short datano_s;
    public short dummy;
    public short datano_e;
    public IODBPMCCNTL1 info = new IODBPMCCNTL1();
  }

  /* pmc_rdalmmsg:read PMC alarm message */
  public static class ODBPMCALM_data extends JnaStructure {

    public String almmsg = StringHelper.repeatChar(' ', 128); // alarm message
  }

  public static class ODBPMCALM extends JnaStructure {
    public ODBPMCALM_data msg1 = new ODBPMCALM_data();
    public ODBPMCALM_data msg2 = new ODBPMCALM_data();
    public ODBPMCALM_data msg3 = new ODBPMCALM_data();
    public ODBPMCALM_data msg4 = new ODBPMCALM_data();
    public ODBPMCALM_data msg5 = new ODBPMCALM_data();
    public ODBPMCALM_data msg6 = new ODBPMCALM_data();
    public ODBPMCALM_data msg7 = new ODBPMCALM_data();
    public ODBPMCALM_data msg8 = new ODBPMCALM_data();
    public ODBPMCALM_data msg9 = new ODBPMCALM_data();
    public ODBPMCALM_data msg10 = new ODBPMCALM_data();
  } // In case that the number of data is 10

  /* pmc_getdtailerr:get detail error for pmc */
  public static class ODBPMCERR extends JnaStructure {
    public short err_no;
    public short err_dtno;
  }

  /* pmc_rdpmctitle:read pmc title data */
  public static class ODBPMCTITLE extends JnaStructure {

    public String mtb = StringHelper.repeatChar(' ', 48);

    public String machine = StringHelper.repeatChar(' ', 48);

    public String type = StringHelper.repeatChar(' ', 48);

    public String prgno = StringHelper.repeatChar(' ', 8);

    public String prgvers = StringHelper.repeatChar(' ', 4);

    public String prgdraw = StringHelper.repeatChar(' ', 48);

    public String date = StringHelper.repeatChar(' ', 32);

    public String design = StringHelper.repeatChar(' ', 48);

    public String written = StringHelper.repeatChar(' ', 48);

    public String remarks = StringHelper.repeatChar(' ', 48);
  }

  /* pmc_rdpmcrng_ext:read PMC data */
  public static class IODBPMCEXT extends JnaStructure {
    public short type_a; // PMC address type
    public short type_d; // PMC data type
    public short datano_s; // start PMC address
    public short datano_e; // end PMC address
    public short err_code; // error code
    public short reserved; // reserved

    public Object data; // pointer to buffer
  }

  /* pmc_rdpmcaddr:read PMC address information */
  public static class ODBPMCADR_info extends JnaStructure {

    public byte pmc_adr;

    public byte adr_attr;

    public short offset;

    public short top;

    public short num;
  }

  public static class ODBPMCADR1 extends JnaStructure {
    public ODBPMCADR_info info1 = new ODBPMCADR_info();
    public ODBPMCADR_info info2 = new ODBPMCADR_info();
    public ODBPMCADR_info info3 = new ODBPMCADR_info();
    public ODBPMCADR_info info4 = new ODBPMCADR_info();
    public ODBPMCADR_info info5 = new ODBPMCADR_info();
    public ODBPMCADR_info info6 = new ODBPMCADR_info();
    public ODBPMCADR_info info7 = new ODBPMCADR_info();
    public ODBPMCADR_info info8 = new ODBPMCADR_info();
    public ODBPMCADR_info info9 = new ODBPMCADR_info();
    public ODBPMCADR_info info10 = new ODBPMCADR_info();
    public ODBPMCADR_info info11 = new ODBPMCADR_info();
    public ODBPMCADR_info info12 = new ODBPMCADR_info();
    public ODBPMCADR_info info13 = new ODBPMCADR_info();
    public ODBPMCADR_info info14 = new ODBPMCADR_info();
    public ODBPMCADR_info info15 = new ODBPMCADR_info();
    public ODBPMCADR_info info16 = new ODBPMCADR_info();
    public ODBPMCADR_info info17 = new ODBPMCADR_info();
    public ODBPMCADR_info info18 = new ODBPMCADR_info();
    public ODBPMCADR_info info19 = new ODBPMCADR_info();
    public ODBPMCADR_info info20 = new ODBPMCADR_info();
    public ODBPMCADR_info info21 = new ODBPMCADR_info();
    public ODBPMCADR_info info22 = new ODBPMCADR_info();
    public ODBPMCADR_info info23 = new ODBPMCADR_info();
    public ODBPMCADR_info info24 = new ODBPMCADR_info();
    public ODBPMCADR_info info25 = new ODBPMCADR_info();
    public ODBPMCADR_info info26 = new ODBPMCADR_info();
    public ODBPMCADR_info info27 = new ODBPMCADR_info();
    public ODBPMCADR_info info28 = new ODBPMCADR_info();
    public ODBPMCADR_info info29 = new ODBPMCADR_info();
    public ODBPMCADR_info info30 = new ODBPMCADR_info();
    public ODBPMCADR_info info31 = new ODBPMCADR_info();
    public ODBPMCADR_info info32 = new ODBPMCADR_info();
    public ODBPMCADR_info info33 = new ODBPMCADR_info();
    public ODBPMCADR_info info34 = new ODBPMCADR_info();
    public ODBPMCADR_info info35 = new ODBPMCADR_info();
    public ODBPMCADR_info info36 = new ODBPMCADR_info();
    public ODBPMCADR_info info37 = new ODBPMCADR_info();
    public ODBPMCADR_info info38 = new ODBPMCADR_info();
    public ODBPMCADR_info info39 = new ODBPMCADR_info();
    public ODBPMCADR_info info40 = new ODBPMCADR_info();
    public ODBPMCADR_info info41 = new ODBPMCADR_info();
    public ODBPMCADR_info info42 = new ODBPMCADR_info();
    public ODBPMCADR_info info43 = new ODBPMCADR_info();
    public ODBPMCADR_info info44 = new ODBPMCADR_info();
    public ODBPMCADR_info info45 = new ODBPMCADR_info();
    public ODBPMCADR_info info46 = new ODBPMCADR_info();
    public ODBPMCADR_info info47 = new ODBPMCADR_info();
    public ODBPMCADR_info info48 = new ODBPMCADR_info();
    public ODBPMCADR_info info49 = new ODBPMCADR_info();
    public ODBPMCADR_info info50 = new ODBPMCADR_info();
    public ODBPMCADR_info info51 = new ODBPMCADR_info();
    public ODBPMCADR_info info52 = new ODBPMCADR_info();
    public ODBPMCADR_info info53 = new ODBPMCADR_info();
    public ODBPMCADR_info info54 = new ODBPMCADR_info();
    public ODBPMCADR_info info55 = new ODBPMCADR_info();
    public ODBPMCADR_info info56 = new ODBPMCADR_info();
    public ODBPMCADR_info info57 = new ODBPMCADR_info();
    public ODBPMCADR_info info58 = new ODBPMCADR_info();
    public ODBPMCADR_info info59 = new ODBPMCADR_info();
    public ODBPMCADR_info info60 = new ODBPMCADR_info();
    public ODBPMCADR_info info61 = new ODBPMCADR_info();
    public ODBPMCADR_info info62 = new ODBPMCADR_info();
    public ODBPMCADR_info info63 = new ODBPMCADR_info();
    public ODBPMCADR_info info64 = new ODBPMCADR_info();
  }

  public static class ODBPMCADR extends JnaStructure {

    public int io_adr;
    public short datano;
    public ODBPMCADR1 info = new ODBPMCADR1();
  }

  /*--------------------------*/
  /* PROFIBUS function        */
  /*--------------------------*/
  /* pmc_prfrdconfig:read PROFIBUS configration data */
  public static class ODBPRFCNF extends JnaStructure {

    public String master_ser = StringHelper.repeatChar(' ', 5);

    public String master_ver = StringHelper.repeatChar(' ', 3);

    public String slave_ser = StringHelper.repeatChar(' ', 5);

    public String slave_ver = StringHelper.repeatChar(' ', 3);

    public String cntl_ser = StringHelper.repeatChar(' ', 5);

    public String cntl_ver = StringHelper.repeatChar(' ', 3);
  }

  /* pmc_prfrdbusprm:read bus parameter for master function */
  /* pmc_prfwrbusprm:write bus parameter for master function */
  public static class IODBBUSPRM extends JnaStructure {
    public byte fdl_add;
    public byte baudrate;

    public short tsl;

    public short min_tsdr;

    public short max_tsdr;

    public byte tqui;

    public byte tset;
    public int ttr;
    public byte gap;
    public byte hsa;
    public byte max_retry;

    public byte bp_flag;

    public short min_slv_int;

    public short poll_tout;

    public short data_cntl;

    public byte[] reserve1 = new byte[6];

    public byte[] cls2_name = new byte[32];
    public short user_dlen;

    public byte[] user_data = new byte[62];

    public byte[] reserve2 = new byte[96];
  }

  /* pmc_prfrdslvprm:read slave parameter for master function */
  /* pmc_prfwrslvprm:write slave parameter for master function */
  public static class IODBSLVPRM extends JnaStructure {
    public short dis_enb;

    public short ident_no;

    public byte slv_flag;

    public byte slv_type;

    public byte[] reserve1 = new byte[12];

    public byte slv_stat;

    public byte wd_fact1;

    public byte wd_fact2;

    public byte min_tsdr;
    public char reserve2;

    public byte grp_ident;
    public short user_plen;

    public byte[] user_pdata = new byte[32];
    public short cnfg_dlen;

    public byte[] cnfg_data = new byte[126];
    public short slv_ulen;

    public byte[] slv_udata = new byte[30];

    public byte[] reserve3 = new byte[8];
  }

  public static class IODBSLVPRM2 extends JnaStructure {
    public short dis_enb;

    public short ident_no;

    public byte slv_flag;

    public byte slv_type;

    public byte[] reserve1 = new byte[12];

    public byte slv_stat;

    public byte wd_fact1;

    public byte wd_fact2;

    public byte min_tsdr;
    public byte reserve2;

    public byte grp_ident;
    public short user_plen;

    public byte[] user_pdata = new byte[206];
    public short cnfg_dlen;

    public byte[] cnfg_data = new byte[126];
    public short slv_ulen;

    public byte[] slv_udata = new byte[30];

    public byte[] reserve3 = new byte[8];
  }

  /* pmc_prfrdallcadr:read allocation address for master function */
  /* pmc_prfwrallcadr:set allocation address for master function */
  public static class IODBPRFADR extends JnaStructure {

    public byte di_size;

    public byte di_type;

    public short di_addr;
    public short reserve1;

    public byte do_size;

    public byte do_type;

    public short do_addr;
    public short reserve2;

    public byte dgn_size;

    public byte dgn_type;

    public short dgn_addr;
  }

  /* pmc_prfrdslvaddr:read allocation address for slave function */
  /* pmc_prfwrslvaddr:set allocation address for slave function */
  public static class IODBSLVADR extends JnaStructure {

    public byte slave_no;

    public byte di_size;

    public byte di_type;

    public short di_addr;

    public byte do_size;

    public byte do_type;

    public short do_addr;

    public byte[] reserve = new byte[7];
  }

  /* pmc_prfrdslvstat:read status for slave function */
  public static class ODBSLVST extends JnaStructure {

    public byte cnfg_stat;

    public byte prm_stat;
    public byte wdg_stat;

    public byte live_stat;
    public short ident_no;
  }

  /* pmc_prfrdslvid:Reads slave index data of master function */
  /* pmc_prfwrslvid:Writes slave index data of master function */
  public static class IODBSLVID extends JnaStructure {
    public short dis_enb;
    public short slave_no;
    public short nsl;

    public byte dgn_size;
    public char dgn_type;

    public short dgn_addr;
  }

  /* pmc_prfrdslvprm2:Reads  slave parameter of master function(2) */
  /* pmc_prfwrslvprm2:Writes slave parameter of master function(2) */
  public static class IODBSLVPRM3 extends JnaStructure {

    public short ident_no;

    public byte slv_flag;

    public byte slv_type;

    public char[] reserve1 = new char[12];

    public byte slv_stat;

    public byte wd_fact1;

    public byte wd_fact2;

    public byte min_tsdr;
    public char reserve2;

    public byte grp_ident;
    public short user_plen;

    public char[] user_pdata = new char[206];
    public short slv_ulen;

    public char[] slv_udata = new char[30];
  }

  /* pmc_prfrddido:Reads DI/DO parameter of master function */
  /* pmc_prfwrdido:Writes DI/DO parameter of master function */
  public static class IODBDIDO extends JnaStructure {
    public short slave_no;
    public short slot_no;

    public byte di_size;
    public char di_type;

    public short di_addr;

    public byte do_size;
    public char do_type;

    public short do_addr;
    public short shift;

    public byte module_dlen;

    public char[] module_data = new char[128];
  }

  /* pmc_prfrdindiadr:Reads indication address of master function */
  /* pmc_prfwrindiadr:Writes indication address of master function */
  public static class IODBINDEADR extends JnaStructure {

    public byte dummy;
    public char indi_type;

    public short indi_addr;
  }

  /*-----------------------------------------------*/
  /* DS : Data server & Ethernet board function    */
  /*-----------------------------------------------*/
  /* etb_rdparam : read�@the parameter of the Ethernet board  */
  /* etb_wrparam : write the parameter of the Ethernet board  */
  public static class TCPPRM extends JnaStructure {

    public String OwnIPAddress = StringHelper.repeatChar(' ', 16);

    public String SubNetMask = StringHelper.repeatChar(' ', 16);

    public String RouterIPAddress = StringHelper.repeatChar(' ', 16);
  }

  public static class HOSTPRM extends JnaStructure {
    public short DataServerPort;

    public String DataServerIPAddress = StringHelper.repeatChar(' ', 16);

    public String DataServerUserName = StringHelper.repeatChar(' ', 32);

    public String DataServerPassword = StringHelper.repeatChar(' ', 32);

    public String DataServerLoginDirectory = StringHelper.repeatChar(' ', 128);
  }

  public static class FTPPRM extends JnaStructure {

    public String FTPServerUserName = StringHelper.repeatChar(' ', 32);

    public String FTPServerPassword = StringHelper.repeatChar(' ', 32);

    public String FTPServerLoginDirectory = StringHelper.repeatChar(' ', 128);
  }

  public static class ETBPRM extends JnaStructure {

    public String OwnMACAddress = StringHelper.repeatChar(' ', 128);
    public short MaximumChannel;
    public short HDDExistence;
    public short NumberOfScreens;
  }

  public static class IODBETP extends JnaStructure {
    public short Dummy_ParameterType;

    public byte[] prm = new byte[210];
  }

  public static class IODBETP_TCP extends JnaStructure {
    public short ParameterType;
    public TCPPRM tcp;
  }

  public static class IODBETP_HOST extends JnaStructure {
    public short ParameterType;
    public HOSTPRM host;
  }

  public static class IODBETP_FTP extends JnaStructure {
    public short ParameterType;
    public FTPPRM ftp;
  }

  public static class IODBETP_ETB extends JnaStructure {
    public short ParameterType;
    public ETBPRM etb;
  }

  /* etb_rderrmsg : read the error message of the Ethernet board */
  public static class ODBETMSG extends JnaStructure {

    public String title = StringHelper.repeatChar(' ', 33);

    public String message = StringHelper.repeatChar(' ', 390);
  }

  /* ds_rdhddinfo : read information of the Data Server's HDD */
  public static class ODBHDDINF extends JnaStructure {
    public int file_num;
    public int remainder_l;
    public int remainder_h;

    public char[] current_dir = new char[32];
  }

  /* ds_rdhdddir : read the file list of the Data Server's HDD */
  public static class ODBHDDDIR_data extends JnaStructure {

    public String file_name = StringHelper.repeatChar(' ', 64);

    public String comment = StringHelper.repeatChar(' ', 80);
    public short attribute;
    public short reserved;
    public int size;

    public String date = StringHelper.repeatChar(' ', 16);
  }

  public static class ODBHDDDIR extends JnaStructure {
    public ODBHDDDIR_data data1 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data2 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data3 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data4 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data5 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data6 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data7 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data8 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data9 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data10 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data11 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data12 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data13 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data14 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data15 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data16 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data17 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data18 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data19 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data20 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data21 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data22 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data23 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data24 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data25 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data26 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data27 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data28 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data29 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data30 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data31 = new ODBHDDDIR_data();
    public ODBHDDDIR_data data32 = new ODBHDDDIR_data();
  }

  /* ds_rdhostdir : read the file list of the host */
  public static class ODBHOSTDIR_data extends JnaStructure {

    public char[] host_file = new char[128];
  }

  public static class ODBHOSTDIR extends JnaStructure {
    public ODBHOSTDIR_data data1 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data2 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data3 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data4 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data5 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data6 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data7 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data8 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data9 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data10 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data11 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data12 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data13 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data14 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data15 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data16 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data17 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data18 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data19 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data20 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data21 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data22 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data23 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data24 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data25 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data26 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data27 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data28 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data29 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data30 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data31 = new ODBHOSTDIR_data();
    public ODBHOSTDIR_data data32 = new ODBHOSTDIR_data();
  }

  /* ds_rdmntinfo : read maintenance information */
  public static class DSMNTINFO extends JnaStructure {

    public short empty_cnt;

    public int total_size;

    public short ReadPtr;

    public short WritePtr;
  }

  /*--------------------------*/
  /* HSSB multiple connection */
  /*--------------------------*/
  /* cnc_rdnodeinfo:read node informations */
  public static class ODBNODE extends JnaStructure {
    public int node_no;
    public int io_base;
    public int status;
    public int cnc_type;

    public String node_name = StringHelper.repeatChar(' ', 20);
  }

  /*-------------------------------------*/
  /* CNC: Control axis / spindle related */
  /*-------------------------------------*/
  /* read actual axis feedrate(F) */
  short cnc_actf(int FlibHndl, ODBACT a);

  /* read absolute axis position */
  short cnc_absolute(int FlibHndl, short a, short b, ODBAXIS c);

  /* read machine axis position */
  short cnc_machine(int FlibHndl, short a, short b, ODBAXIS c);

  /* read relative axis position */
  short cnc_relative(int FlibHndl, short a, short b, ODBAXIS c);

  /* read distance to go */
  short cnc_distance(int FlibHndl, short a, short b, ODBAXIS c);

  /* read skip position */
  short cnc_skip(int FlibHndl, short a, short b, ODBAXIS c);

  /* read servo delay value */
  short cnc_srvdelay(int FlibHndl, short a, short b, ODBAXIS c);

  /* read acceleration/deceleration delay value */
  short cnc_accdecdly(int FlibHndl, short a, short b, ODBAXIS c);

  /* read all dynamic data */
  //#if (!ONO8D)
  short cnc_rddynamic(int FlibHndl, short a, short b, ODBDY_1 c);

  short cnc_rddynamic(int FlibHndl, short a, short b, ODBDY_2 c);

  //#else
//  short cnc_rddynamic(int FlibHndl, short a, short b, ODBDY_1 c);
//  short cnc_rddynamic(int FlibHndl, short a, short b, ODBDY_2 c);
//#endif
  /* read all dynamic data */
  short cnc_rddynamic2(int FlibHndl, short a, short b, ODBDY2_1 c);

  short cnc_rddynamic2(int FlibHndl, short a, short b, ODBDY2_2 c);

  /* read actual spindle speed(S) */
  short cnc_acts(int FlibHndl, ODBACT a);

  /* read actual spindle speed(S) (All or spesified) */
  short cnc_acts2(long FlibHndl, short a, ODBACT2[] b);

  /* set origin / preset relative axis position */
  short cnc_wrrelpos(int FlibHndl, short a, IDBWRR b);

  /* preset work coordinate */
  short cnc_prstwkcd(int FlibHndl, short a, IDBWRA b);

  /* read manual overlapped motion value */
  short cnc_rdmovrlap(int FlibHndl, short a, short b, IODBOVL c);

  /* cancel manual overlapped motion value */
  short cnc_canmovrlap(int FlibHndl, short a);

  /* read load information of serial spindle */
  short cnc_rdspload(int FlibHndl, short a, ODBSPN[] b);

  /* read maximum r.p.m. ratio of serial spindle */
  short cnc_rdspmaxrpm(int FlibHndl, short a, ODBSPN b);

  /* read gear ratio of serial spindle */
  short cnc_rdspgear(int FlibHndl, short a, ODBSPN b);

  /* read absolute axis position 2 */
  short cnc_absolute2(int FlibHndl, short a, short b, ODBAXIS c);

  /* read relative axis position 2 */
  short cnc_relative2(int FlibHndl, short a, short b, ODBAXIS c);

  /* set wire vertival position */
  short cnc_setvrtclpos(int FlibHndl, short a);

  /* set wire threading position */
  short cnc_setthrdngpos(int FlibHndl);

  /* read tool position */
  short cnc_rdposition(int FlibHndl, short a, int[] b, ODBPOS c);

  /* read current speed */
  short cnc_rdspeed(int FlibHndl, short a, ODBSPEED b);

  /* read servo load meter */
  short cnc_rdsvmeter(int FlibHndl, RefObject<Short> a, ODBSVLOAD b);

  /* read spindle load meter */
  short cnc_rdspmeter(int FlibHndl, short a, RefObject<Short> b, ODBSPLOAD c);

  /* read manual feed for 5-axis machining */
  short cnc_rd5axmandt(int FlibHndl, ODB5AXMAN a);

  /* read amount of machine axes movement of manual feed for 5-axis machining */
  short cnc_rd5axovrlap(int FlibHndl, short a, short b, ODBAXIS c);

  /* read handle interruption */
  short cnc_rdhndintrpt(int FlibHndl, short a, RefObject<Short> b, ODBHND c);

  /* clear pulse values of manual feed for 5-axis machining */
  short cnc_clr5axpls(int FlibHndl, short a);

  /* read constant surface speed */
  short cnc_rdspcss(int FlibHndl, ODBCSS a);

  /* read execution program pointer */
  short cnc_rdexecpt(int FlibHndl, PRGPNT a, PRGPNT b);

  /* read various axis data */
  short cnc_rdaxisdata(int FlibHndl, short a, Object b, short c, RefObject<Short> d, ODBAXDT e);

  /*----------------------*/
  /* CNC: Program related */
  /*----------------------*/
  /* start downloading NC program */
  short cnc_dwnstart(int FlibHndl);

  /* download NC program */
  short cnc_download(int FlibHndl, Object a, short b);

  /* download NC program(conditional) */
  short cnc_cdownload(int FlibHndl, Object a, short b);

  /* end of downloading NC program */
  short cnc_dwnend(int FlibHndl);

  /* end of downloading NC program 2 */
  short cnc_dwnend2(int FlibHndl, Object a);

  /* start downloading NC program 3 */
  short cnc_dwnstart3(int FlibHndl, short a);

  /* start downloading NC program 3 special */
  short cnc_dwnstart3_f(int FlibHndl, short a, Object b, Object c);

  /* download NC program 3 */
  short cnc_download3(int FlibHndl, RefObject<Integer> a, Object b);

  /* end of downloading NC program 3 */
  short cnc_dwnend3(int FlibHndl);

  /* start downloading NC program 4 */
  short cnc_dwnstart4(int FlibHndl, short a, Object b);

  /* download NC program 4 */
  short cnc_download4(int FlibHndl, RefObject<Integer> a, Object b);

  /* end of downloading NC program 4 */
  short cnc_dwnend4(int FlibHndl);

  /* start verification of NC program */
  short cnc_vrfstart(int FlibHndl);

  /* verify NC program */
  short cnc_verify(int FlibHndl, Object a, short b);

  /* verify NC program(conditional) */
  short cnc_cverify(int FlibHndl, Object a, short b);

  /* end of verification */
  short cnc_vrfend(int FlibHndl);

  /* start verification of NC program */
  short cnc_vrfstart4(int FlibHndl, Object a);

  /* verify NC program */
  short cnc_verify4(int FlibHndl, RefObject<Integer> a, Object b);

  /* end of verification */
  short cnc_vrfend4(int FlibHndl);

  /* start downloading DNC program */
  short cnc_dncstart(int FlibHndl);

  /* download DNC program */
  short cnc_dnc(int FlibHndl, Object a, short b);

  /* download DNC program(conditional) */
  short cnc_cdnc(int FlibHndl, Object a, short b);

  /* end of downloading DNC program */
  short cnc_dncend(int FlibHndl);

  /* start downloading DNC program 2 */
  short cnc_dncstart2(int FlibHndl, Object a);

  /* download DNC program 2 */
  short cnc_dnc2(int FlibHndl, RefObject<Integer> a, Object b);

  /* end of downloading DNC program 2 */
  short cnc_dncend2(int FlibHndl, short a);

  /* read the diagnosis data of DNC operation */
  short cnc_rddncdgndt(int FlibHndl, ODBDNCDGN a);

  /* start uploading NC program */
  //#if (!ONO8D)
  short cnc_upstart(int FlibHndl, short a);

  //#else
  short cnc_upstart(int FlibHndl, int a);

  //#endif
  /* upload NC program */
  short cnc_upload(int FlibHndl, ODBUP a, RefObject<Short> b);

  /* upload NC program(conditional) */
  short cnc_cupload(int FlibHndl, ODBUP a, RefObject<Short> b);

  /* end of uploading NC program */
  short cnc_upend(int FlibHndl);

  /* start uploading NC program 3 */
  short cnc_upstart3(int FlibHndl, short a, int b, int c);

  /* start uploading NC program special 3 */
  short cnc_upstart3_f(int FlibHndl, short a, Object b, Object c);

  /* upload NC program 3 */
  short cnc_upload3(int FlibHndl, RefObject<Integer> a, Object b);

  /* end of uploading NC program 3 */
  short cnc_upend3(int FlibHndl);

  /* start uploading NC program 4 */
  short cnc_upstart4(int FlibHndl, short a, Object b);

  /* upload NC program 4 */
  short cnc_upload4(int FlibHndl, RefObject<Integer> a, Object b);

  /* end of uploading NC program 4 */
  short cnc_upend4(int FlibHndl);

  /* read buffer status for downloading/verification NC program */
  short cnc_buff(int FlibHndl, ODBBUF a);

  /* search specified program */
  //#if (!ONO8D)
  short cnc_search(int FlibHndl, short a);

  //#else
  short cnc_search(int FlibHndl, int a);

  //#endif
  /* search specified program */
  short cnc_search2(int FlibHndl, int a);

  /* delete all programs */
  short cnc_delall(int FlibHndl);

  /* delete specified program */
  //#if (!ONO8D)
  short cnc_delete(int FlibHndl, short a);

  //#else
  short cnc_delete(int FlibHndl, int a);

  //#endif
  /* delete program (area specified) */
  short cnc_delrange(int FlibHndl, int a, int b);
  /* read program directory */
//#if (!ONO8D)
  /*short cnc_rdprogdir(int FlibHndl, short a, short b, short c, short d, PRGDIR e);*/
  //#else

  short cnc_rdprogdir(int FlibHndl, short a, short b, short c, short d, PRGDIR e);

  //#endif
  /* read program information */
  short cnc_rdproginfo(int FlibHndl, short a, short b, ODBNC_1 c);

  short cnc_rdproginfo(int FlibHndl, short a, short b, ODBNC_2 c);
  /* read program number under execution */
//#if (!ONO8D)
  /*short cnc_rdprgnum(int FlibHndl, ODBPRO a);*/
  //#else

  short cnc_rdprgnum(int FlibHndl, ODBPRO a);

  //#endif
  /* read program name under execution */
  short cnc_exeprgname(int FlibHndl, ODBEXEPRG a);

  /* read sequence number under execution */
  short cnc_rdseqnum(int FlibHndl, ODBSEQ a);

  /* search specified sequence number */
  short cnc_seqsrch(int FlibHndl, int a);

  /* search specified sequence number (2) */
  short cnc_seqsrch2(int FlibHndl, int a);

  /* rewind cursor of NC program */
  short cnc_rewind(int FlibHndl);

  /* read block counter */
  short cnc_rdblkcount(int FlibHndl, OutObject<Integer> a);

  /* read program under execution */
  short cnc_rdexecprog(int FlibHndl, RefObject<Short> a, OutObject<Short> b, Object c);

  /* read program for MDI operation */
  short cnc_rdmdiprog(int FlibHndl, RefObject<Short> a, Object b);

  /* write program for MDI operation */
  short cnc_wrmdiprog(int FlibHndl, short a, Object b);

  /* read execution pointer for MDI operation */
  //#if (!ONO8D)
  short cnc_rdmdipntr(int FlibHndl, ODBMDIP a);

  //#else
  /*short cnc_rdmdipntr(int FlibHndl, ODBMDIP a);*/
//#endif
  /* write execution pointer for MDI operation */
  short cnc_wrmdipntr(int FlibHndl, int a);

  /* register new program */
  short cnc_newprog(int FlibHndl, int a);

  /* copy program */
  short cnc_copyprog(int FlibHndl, int a, int b);

  /* rename program */
  short cnc_renameprog(int FlibHndl, int a, int b);

  /* condense program */
  short cnc_condense(int FlibHndl, short a, int b);

  /* merge program */
  short cnc_mergeprog(int FlibHndl, short a, int b, int c, int d);

  /* read current program and its pointer */
  short cnc_rdactpt(int FlibHndl, OutObject<Integer> a, OutObject<Integer> b);

  /* read current program and its pointer and UV macro pointer */
  short cnc_rduvactpt(int FlibHndl, OutObject<Integer> a, OutObject<Integer> b, OutObject<Integer> c);

  /* set current program and its pointer */
  short cnc_wractpt(int FlibHndl, int a, short b, RefObject<Integer> c);

  /* line edit (read program) */
  short cnc_rdprogline(int FlibHndl, int a, int b, Object c, RefObject<Integer> d, RefObject<Integer> e);

  /* line edit (read program) */
  short cnc_rdprogline2(int FlibHndl, int a, int b, Object c, RefObject<Integer> d, RefObject<Integer> e);

  /* line edit (write program) */
  short cnc_wrprogline(int FlibHndl, int a, int b, Object c, int d);

  /* line edit (delete line in program) */
  short cnc_delprogline(int FlibHndl, int a, int b, int c);

  /* line edit (search string) */
  short cnc_searchword(int FlibHndl, int a, int b, short c, short d, int e, Object f);

  /* line edit (search string) */
  short cnc_searchresult(int FlibHndl, OutObject<Integer> a);

  /* line edit (read program by file name) */
  short cnc_rdpdf_line(int FlibHndl, Object a, int b, Object c, RefObject<Integer> d, RefObject<Integer> e);

  /* program lock */
  short cnc_setpglock(int FlibHndl, int a);

  /* program unlock */
  short cnc_resetpglock(int FlibHndl, int a);

  /* read the status of the program lock */
  short cnc_rdpglockstat(int FlibHndl, OutObject<Integer> a, OutObject<Integer> b);

  /* create file or directory */
  short cnc_pdf_add(int FlibHndl, Object a);

  /* condense program file */
  short cnc_pdf_cond(int FlibHndl, Object a);

  /* change attribute of program file and directory */
  short cnc_wrpdf_attr(int FlibHndl, Object a, IDBPDFTDIR b);

  /* copy program file */
  short cnc_pdf_copy(int FlibHndl, Object a, Object b);

  /* delete file or directory */
  short cnc_pdf_del(int FlibHndl, Object a);

  /* line edit (write program by file name) */
  short cnc_wrpdf_line(int FlibHndl, Object a, int b, Object c, int d);

  /* line edit (delete line by file name) */
  short cnc_pdf_delline(int FlibHndl, Object a, int b, int c);

  /* move program file */
  short cnc_pdf_move(int FlibHndl, Object a, Object b);

  /* read current program and its pointer */
  short cnc_pdf_rdactpt(int FlibHndl, Object a, OutObject<Integer> b);

  /* read selected file name */
  short cnc_pdf_rdmain(int FlibHndl, Object a);

  /* rename file or directory */
  short cnc_pdf_rename(int FlibHndl, Object a, Object b);

  /* line edit (search string) */
  short cnc_pdf_searchword(int FlibHndl, Object a, int b, int c, int d, int e, Object f);

  /* line edit (search string) */
  short cnc_pdf_searchresult(int FlibHndl, OutObject<Integer> a);

  /* select program file */
  short cnc_pdf_slctmain(int FlibHndl, Object a);

  /* set current program and its pointer */
  short cnc_pdf_wractpt(int FlibHndl, Object a, short b, RefObject<Integer> c);

  /* read program drive information */
  short cnc_rdpdf_inf(int FlibHndl, Object a, short b, Object c);

  /* read program drive directory */
  short cnc_rdpdf_drive(int FlibHndl, Object a);

  /* read current directory */
  short cnc_rdpdf_curdir(int FlibHndl, short a, Object b);

  /* set current directory */
  short cnc_wrpdf_curdir(int FlibHndl, short a, Object b);

  /* read directory (sub directories) */
  short cnc_rdpdf_subdir(int FlibHndl, RefObject<Short> a, IDBPDFSDIR b, ODBPDFSDIR c);

  /* read directory (all files) */
  short cnc_rdpdf_alldir(int FlibHndl, RefObject<Short> a, Object b, Object c);

  /* read file count in directory */
  short cnc_rdpdf_subdirn(int FlibHndl, Object a, ODBPDFNFIL b);

  /*---------------------------*/
  /* CNC: NC file data related */
  /*---------------------------*/
  /* read tool offset value */
  short cnc_rdtofs(int FlibHndl, short a, short b, short c, ODBTOFS d);

  /* write tool offset value */
  short cnc_wrtofs(int FlibHndl, short a, short b, short c, int d);

  /* read tool offset value(area specified) */
  short cnc_rdtofsr(int FlibHndl, short a, short b, short c, short d, IODBTO_1_1 e);

  short cnc_rdtofsr(int FlibHndl, short a, short b, short c, short d, IODBTO_1_2 e);

  short cnc_rdtofsr(int FlibHndl, short a, short b, short c, short d, IODBTO_1_3 e);

  short cnc_rdtofsr(int FlibHndl, short a, short b, short c, short d, IODBTO_2 e);

  short cnc_rdtofsr(int FlibHndl, short a, short b, short c, short d, IODBTO_3 e);

  /* write tool offset value(area specified) */
  short cnc_wrtofsr(int FlibHndl, short a, IODBTO_1_1 b);

  short cnc_wrtofsr(int FlibHndl, short a, IODBTO_1_2 b);

  short cnc_wrtofsr(int FlibHndl, short a, IODBTO_1_3 b);

  short cnc_wrtofsr(int FlibHndl, short a, IODBTO_2 b);

  short cnc_wrtofsr(int FlibHndl, short a, IODBTO_3 b);

  /* read work zero offset value */
  short cnc_rdzofs(int FlibHndl, short a, short b, short c, IODBZOFS d);

  /* write work zero offset value */
  short cnc_wrzofs(int FlibHndl, short a, IODBZOFS b);

  /* read work zero offset value(area specified) */
  short cnc_rdzofsr(int FlibHndl, short a, short b, short c, short d, IODBZOR e);

  /* write work zero offset value(area specified) */
  short cnc_wrzofsr(int FlibHndl, short a, IODBZOR b);

  /* read mesured point value */
  short cnc_rdmsptype(int FlibHndl, short a, short b, short c, IODBMSTP d);

  /* write mesured point value */
  short cnc_wrmsptype(int FlibHndl, short a, IODBMSTP d);

  /* read parameter */
  short cnc_rdparam(int FlibHndl, short a, short b, short c, IODBPSD_1 d);

  short cnc_rdparam(int FlibHndl, short a, short b, short c, IODBPSD_2 d);

  short cnc_rdparam(int FlibHndl, short a, short b, short c, IODBPSD_3 d);

  short cnc_rdparam(int FlibHndl, short a, short b, short c, IODBPSD_4 d);

  /* write parameter */
  short cnc_wrparam(int FlibHndl, short a, IODBPSD_1 b);

  short cnc_wrparam(int FlibHndl, short a, IODBPSD_2 b);

  short cnc_wrparam(int FlibHndl, short a, IODBPSD_3 b);

  short cnc_wrparam(int FlibHndl, short a, IODBPSD_4 b);

  /* read parameter(area specified) */

  public static class REALPRM2 extends JnaStructure {     /* real parameter */
   public int prm_val;             /* value of variable */
    public  int dec_val;             /* number of places of decimals */
  }

  short cnc_rdparar(int FlibHndl, short[] a, short b, short[] c, short[] d, Object e);

  //  [DllImport("FWLIB32.dll", EntryPoint="cnc_rdparar")]
  //  public static extern short cnc_rdparar( uint FlibHndl,
  //      ref short a, short b, ref short c, ref short d, [Out,MarshalAs(UnmanagedType.LPStruct)] IODBPSD_A e );
  //  [DllImport("FWLIB32.dll", EntryPoint="cnc_rdparar")]
  //  public static extern short cnc_rdparar( uint FlibHndl,
  //      ref short a, short b, ref short c, ref short d, [Out,MarshalAs(UnmanagedType.LPStruct)] IODBPSD_B e );
  //  [DllImport("FWLIB32.dll", EntryPoint="cnc_rdparar")]
  //  public static extern short cnc_rdparar( uint FlibHndl,
  //      ref short a, short b, ref short c, ref short d, [Out,MarshalAs(UnmanagedType.LPStruct)] IODBPSD_C e );
  //  [DllImport("FWLIB32.dll", EntryPoint="cnc_rdparar")]
  //  public static extern short cnc_rdparar( uint FlibHndl,
  //      ref short a, short b, ref short c, ref short d, [Out,MarshalAs(UnmanagedType.LPStruct)] IODBPSD_D e );
  /* write parameter(area specified) */
  short cnc_wrparas(int FlibHndl, short a, Object b);

  /* read setting data */
  short cnc_rdset(int FlibHndl, short a, short b, short c, IODBPSD_1 d);

  short cnc_rdset(int FlibHndl, short a, short b, short c, IODBPSD_2 d);

  short cnc_rdset(int FlibHndl, short a, short b, short c, IODBPSD_3 d);

  short cnc_rdset(int FlibHndl, short a, short b, short c, IODBPSD_4 d);

  /* write setting data */
  short cnc_wrset(int FlibHndl, short a, IODBPSD_1 b);

  short cnc_wrset(int FlibHndl, short a, IODBPSD_2 b);

  short cnc_wrset(int FlibHndl, short a, IODBPSD_3 b);

  short cnc_wrset(int FlibHndl, short a, IODBPSD_4 b);

  /* read setting data(area specified) */
  short cnc_rdsetr(int FlibHndl, RefObject<Short> a, short b, RefObject<Short> c, RefObject<Short> d, Object e);

  /* write setting data(area specified) */
  short cnc_wrsets(int FlibHndl, short a, Object b);

  /* read parameters */
  short cnc_rdparam_ext(int FlibHndl, IODBPRMNO a, short b, IODBPRM c);

  /* read parameter */
  short cnc_rdparam3(int FlibHndl, short a, short b, short c, short d, IODBPSD_1 e);

  short cnc_rdparam3(int FlibHndl, short a, short b, short c, short d, IODBPSD_2 e);

  short cnc_rdparam3(int FlibHndl, short a, short b, short c, short d, IODBPSD_3 e);

  short cnc_rdparam3(int FlibHndl, short a, short b, short c, short d, IODBPSD_4 e);

  /* async parameter write start */
  short cnc_start_async_wrparam(int FlibHndl, IODBPRM a);

  /* async parameter write end */
  short cnc_end_async_wrparam(int FlibHndl, OutObject<Short> a);

  /* read cause of busy for async parameter write */
  short cnc_async_busy_state(int FlibHndl, OutObject<Short> a);

  /* read diagnosis data */
  short cnc_rddiag_ext(int FlibHndl, IODBPRMNO a, short b, IODBPRM c);

  /* read pitch error compensation data(area specified) */
  short cnc_rdpitchr(int FlibHndl, short a, short b, short c, IODBPI d);

  /* write pitch error compensation data(area specified) */
  short cnc_wrpitchr(int FlibHndl, short a, IODBPI b);

  /* read custom macro variable */
  short cnc_rdmacro(int FlibHndl, short a, short b, ODBM c);

  /* write custom macro variable */
  short cnc_wrmacro(int FlibHndl, short a, short b, int c, short d);

  /* read custom macro variables(area specified) */
  short cnc_rdmacror(int FlibHndl, short a, short b, short c, IODBMR d);

  /* write custom macro variables(area specified) */
  short cnc_wrmacror(int FlibHndl, short a, IODBMR b);

  /* read custom macro variables(IEEE double version) */
  short cnc_rdmacror2(int FlibHndl, int a, RefObject<Integer> b, Object c);

  /* write custom macro variables(IEEE double version) */
  short cnc_wrmacror2(int FlibHndl, int a, RefObject<Integer> b, Object c);

  /* read P code macro variable */
  short cnc_rdpmacro(int FlibHndl, int a, ODBPM b);

  /* write P code macro variable */
  short cnc_wrpmacro(int FlibHndl, int a, int b, short c);

  /* read P code macro variables(area specified) */
  short cnc_rdpmacror(int FlibHndl, int a, int b, short c, IODBPR d);

  /* write P code macro variables(area specified) */
  short cnc_wrpmacror(int FlibHndl, short a, IODBPR b);

  /* read P code macro variables(IEEE double version) */
  short cnc_rdpmacror2(int FlibHndl, int a, RefObject<Integer> b, short c, Object d);

  /* write P code macro variables(IEEE double version) */
  short cnc_wrpmacror2(int FlibHndl, int a, RefObject<Integer> b, short c, Object d);

  /* read tool offset information */
  short cnc_rdtofsinfo(int FlibHndl, ODBTLINF a);

  /* read tool offset information(2) */
  short cnc_rdtofsinfo2(int FlibHndl, ODBTLINF2 a);

  /* read work zero offset information */
  short cnc_rdzofsinfo(int FlibHndl, OutObject<Short> a);

  /* read pitch error compensation data information */
  short cnc_rdpitchinfo(int FlibHndl, OutObject<Short> a);

  /* read custom macro variable information */
  short cnc_rdmacroinfo(int FlibHndl, ODBMVINF a);

  /* read P code macro variable information */
  short cnc_rdpmacroinfo(int FlibHndl, ODBPMINF a);

  /* read validity of tool offset */
  short cnc_tofs_rnge(int FlibHndl, short a, short b, ODBDATRNG c);

  /* read validity of work zero offset */
  short cnc_zofs_rnge(int FlibHndl, short a, short b, ODBDATRNG c);

  /* read validity of work zero offset */
  short cnc_wksft_rnge(int FlibHndl, short a, ODBDATRNG b);

  /* read the information for function cnc_rdhsparam() */
  short cnc_rdhsprminfo(int FlibHndl, int a, HSPINFO_data b);

  /* read parameters at the high speed */
  short cnc_rdhsparam(int FlibHndl, int a, HSPINFO b, HSPDATA_1 c);

  short cnc_rdhsparam(int FlibHndl, int a, HSPINFO b, HSPDATA_2 c);

  short cnc_rdhsparam(int FlibHndl, int a, HSPINFO b, HSPDATA_3 c);

  /*----------------------------------------*/
  /* CNC: Tool life management data related */
  /*----------------------------------------*/
  /* read tool life management data(tool group number) */
  short cnc_rdgrpid(int FlibHndl, short a, ODBTLIFE1 b);

  /* read tool life management data(number of tool groups) */
  short cnc_rdngrp(int FlibHndl, ODBTLIFE2 a);

  /* read tool life management data(number of utils) */
  short cnc_rdntool(int FlibHndl, short a, ODBTLIFE3 b);

  /* read tool life management data(tool life) */
  short cnc_rdlife(int FlibHndl, short a, ODBTLIFE3 b);

  /* read tool life management data(tool lift counter) */
  short cnc_rdcount(int FlibHndl, short a, ODBTLIFE3 b);

  /* read tool life management data(tool length number-1) */
  short cnc_rd1length(int FlibHndl, short a, short b, ODBTLIFE4 c);

  /* read tool life management data(tool length number-2) */
  short cnc_rd2length(int FlibHndl, short a, short b, ODBTLIFE4 c);

  /* read tool life management data(cutter compensation no.-1) */
  short cnc_rd1radius(int FlibHndl, short a, short b, ODBTLIFE4 c);

  /* read tool life management data(cutter compensation no.-2) */
  short cnc_rd2radius(int FlibHndl, short a, short b, ODBTLIFE4 c);

  /* read tool life management data(tool information-1) */
  short cnc_t1info(int FlibHndl, short a, short b, ODBTLIFE4 c);

  /* read tool life management data(tool information-2) */
  short cnc_t2info(int FlibHndl, short a, short b, ODBTLIFE4 c);

  /* read tool life management data(tool number) */
  short cnc_toolnum(int FlibHndl, short a, short b, ODBTLIFE4 c);

  /* read tool life management data(tool number, tool life, tool life counter)(area specified) */
  short cnc_rdtoolrng(int FlibHndl, short a, short b, short c, IODBTR d);

  /* read tool life management data(all data within group) */
  short cnc_rdtoolgrp(int FlibHndl, short a, short b, ODBTG c);

  /* write tool life management data(tool life counter) (area specified) */
  short cnc_wrcountr(int FlibHndl, short a, IDBWRC b);

  /* read tool life management data(used tool group number) */
  short cnc_rdusegrpid(int FlibHndl, ODBUSEGR a);

  /* read tool life management data(max. number of tool groups) */
  short cnc_rdmaxgrp(int FlibHndl, ODBLFNO a);

  /* read tool life management data(maximum number of tool within group) */
  short cnc_rdmaxtool(int FlibHndl, ODBLFNO a);

  /* read tool life management data(used tool no. within group) */
  short cnc_rdusetlno(int FlibHndl, short a, short b, short c, ODBTLUSE d);

  /* read tool life management data(tool data1) */
  short cnc_rd1tlifedata(int FlibHndl, short a, short b, IODBTD c);

  /* read tool life management data(tool data2) */
  short cnc_rd2tlifedata(int FlibHndl, short a, short b, IODBTD c);

  /* write tool life management data(tool data1) */
  short cnc_wr1tlifedata(int FlibHndl, IODBTD a);

  /* write tool life management data(tool data2) */
  short cnc_wr2tlifedata(int FlibHndl, IODBTD a);

  /* read tool life management data(tool group information) */
  short cnc_rdgrpinfo(int FlibHndl, short a, short b, short c, IODBTGI d);

  /* read tool life management data(tool group information 2) */
  short cnc_rdgrpinfo2(int FlibHndl, short a, short b, short c, IODBTGI2 d);

  /* read tool life management data(tool group information 3) */
  short cnc_rdgrpinfo3(int FlibHndl, short a, short b, short c, IODBTGI3 d);

  /* read tool life management data(tool group information 4) */
  short cnc_rdgrpinfo4(int FlibHndl, short a, short b, short c, OutObject<Short> d, IODBTGI4 e);

  /* write tool life management data(tool group information) */
  short cnc_wrgrpinfo(int FlibHndl, short a, IODBTGI b);

  /* write tool life management data(tool group information 2) */
  short cnc_wrgrpinfo2(int FlibHndl, short a, IODBTGI2 b);

  /* write tool life management data(tool group information 3) */
  short cnc_wrgrpinfo3(int FlibHndl, short a, IODBTGI3 b);

  /* delete tool life management data(tool group) */
  short cnc_deltlifegrp(int FlibHndl, short a);

  /* insert tool life management data(tool data) */
  short cnc_instlifedt(int FlibHndl, IDBITD a);

  /* delete tool life management data(tool data) */
  short cnc_deltlifedt(int FlibHndl, short a, short b);

  /* clear tool life management data(tool life counter, tool information)(area specified) */
  short cnc_clrcntinfo(int FlibHndl, short a, short b);

  /* read tool life management data(tool group number) 2 */
  short cnc_rdgrpid2(int FlibHndl, int a, ODBTLIFE5 b);

  /* read tool life management data(tool data1) 2 */
  short cnc_rd1tlifedat2(int FlibHndl, short a, int b, IODBTD2 c);

  /* write tool life management data(tool data1) 2 */
  short cnc_wr1tlifedat2(int FlibHndl, IODBTD2 a);

  /* read tool life management data */
  short cnc_rdtlinfo(int FlibHndl, ODBTLINFO a);

  /* read tool life management data(used tool group number) */
  short cnc_rdtlusegrp(int FlibHndl, ODBUSEGRP a);

  /* read tool life management data(tool group information 2) */
  short cnc_rdtlgrp(int FlibHndl, int a, RefObject<Short> b, IODBTLGRP c);

  /* read tool life management data (tool data1) */
  short cnc_rdtltool(int FlibHndl, int a, int b, RefObject<Short> c, IODBTLTOOL d);

  short cnc_rdexchgtgrp(int FlibHndl, RefObject<Short> b, ODBEXGP c);

  /*-----------------------------------*/
  /* CNC: Tool management data related */
  /*-----------------------------------*/
  /* new registration of tool management data */
  short cnc_regtool(int FlibHndl, short a, RefObject<Short> b, IODBTLMNG c);

  /* new registration of tool management data */
  short cnc_regtool_f2(int FlibHndl, short a, RefObject<Short> b, IODBTLMNG_F2 c);

  /* deletion of tool management data */
  short cnc_deltool(int FlibHndl, short a, RefObject<Short> b);

  /* lead of tool management data */
  short cnc_rdtool(int FlibHndl, short a, RefObject<Short> b, IODBTLMNG c);

  /* lead of tool management data */
  short cnc_rdtool_f2(int FlibHndl, short a, RefObject<Short> b, IODBTLMNG_F2 c);

  /* write of tool management data */
  short cnc_wrtool(int FlibHndl, short a, IODBTLMNG b);

  /* write of individual data of tool management data */
  short cnc_wrtool2(int FlibHndl, short a, IDBTLM b);

  /* write tool management data */
  short cnc_wrtool_f2(int FlibHndl, short a, IODBTLMNG_F2_data b);

  /* new registration of magazine management data */
  short cnc_regmagazine(int FlibHndl, RefObject<Short> a, IODBTLMAG b);

  /* deletion of magazine management data */
  short cnc_delmagazine(int FlibHndl, RefObject<Short> a, IODBTLMAG2 b);

  /* lead of magazine management data */
  short cnc_rdmagazine(int FlibHndl, RefObject<Short> a, IODBTLMAG b);

  /* Individual write of magazine management data */
  short cnc_wrmagazine(int FlibHndl, short a, short b, short c);

  /*-------------------------------------*/
  /* CNC: Operation history data related */
  /*-------------------------------------*/
  /* stop logging operation history data */
  short cnc_stopophis(int FlibHndl);

  /* restart logging operation history data */
  short cnc_startophis(int FlibHndl);

  /* read number of operation history data */
  short cnc_rdophisno(int FlibHndl, OutObject<Short> a);

  /* read operation history data */
  short cnc_rdophistry(int FlibHndl, short a, short b, short c, ODBHIS d);

  /* read operation history data */
  short cnc_rdophistry2(int FlibHndl, short a, RefObject<Short> b, RefObject<Short> c, Object d);

  /* read operation history data F30i*/
  short cnc_rdophistry4(int FlibHndl, short a, RefObject<Short> b, RefObject<Short> c, ODBOPHIS4_1 d);

  short cnc_rdophistry4(int FlibHndl, short a, RefObject<Short> b, RefObject<Short> c, ODBOPHIS4_2 d);

  short cnc_rdophistry4(int FlibHndl, short a, RefObject<Short> b, RefObject<Short> c, ODBOPHIS4_3 d);

  short cnc_rdophistry4(int FlibHndl, short a, RefObject<Short> b, RefObject<Short> c, ODBOPHIS4_4 d);

  short cnc_rdophistry4(int FlibHndl, short a, RefObject<Short> b, RefObject<Short> c, ODBOPHIS4_5 d);

  short cnc_rdophistry4(int FlibHndl, short a, RefObject<Short> b, RefObject<Short> c, ODBOPHIS4_6 d);

  short cnc_rdophistry4(int FlibHndl, short a, RefObject<Short> b, RefObject<Short> c, ODBOPHIS4_7 d);

  short cnc_rdophistry4(int FlibHndl, short a, RefObject<Short> b, RefObject<Short> c, ODBOPHIS4_8 d);

  short cnc_rdophistry4(int FlibHndl, short a, RefObject<Short> b, RefObject<Short> c, ODBOPHIS4_9 d);

  short cnc_rdophistry4(int FlibHndl, short a, RefObject<Short> b, RefObject<Short> c, ODBOPHIS4_10 d);

  short cnc_rdophistry4(int FlibHndl, short a, RefObject<Short> b, RefObject<Short> c, ODBOPHIS4_11 d);

  /* read number of alarm history data */
  short cnc_rdalmhisno(int FlibHndl, OutObject<Short> a);

  /* read alarm history data */
  short cnc_rdalmhistry(int FlibHndl, short a, short b, short c, ODBAHIS d);

  /* read alarm history data */
  short cnc_rdalmhistry_w(int FlibHndl, short a, short b, short c, ODBAHIS d);

  /* read alarm history data */
  short cnc_rdalmhistry2(int FlibHndl, short a, short b, short c, ODBAHIS2 d);

  /* read alarm history data F30i*/
  short cnc_rdalmhistry3(int FlibHndl, short a, short b, short c, ODBAHIS3 d);

  /* read alarm history data F30i*/
  short cnc_rdalmhistry5(int FlibHndl, short a, short b, short c, ODBAHIS5 d);

  /* clear operation history data */
  short cnc_clearophis(int FlibHndl, short a);

  /* read signals related operation history */
  short cnc_rdhissgnl(int FlibHndl, IODBSIG a);

  /* read signals related operation history 2 */
  short cnc_rdhissgnl2(int FlibHndl, IODBSIG2 a);

  /* read signals related operation history 3 */
  short cnc_rdhissgnl3(int FlibHndl, IODBSIG3 a);

  /* write signals related operation history */
  short cnc_wrhissgnl(int FlibHndl, IODBSIG a);

  /* write signals related operation history 2 */
  short cnc_wrhissgnl2(int FlibHndl, IODBSIG2 a);

  /* write signals related operation history for F30i*/
  short cnc_wrhissgnl3(int FlibHndl, IODBSIG3 a);

  /* read number of operater message history data */
  short cnc_rdomhisno(int FlibHndl, OutObject<Short> a);

  /*-------------*/
  /* CNC: Others */
  /*-------------*/
  /* read CNC system information */
  short cnc_sysinfo(int FlibHndl, ODBSYS a);

  /* read CNC status information */
  short cnc_statinfo(int FlibHndl, ODBST a);

  /* read alarm status */
  short cnc_alarm(int FlibHndl, ODBALM a);

  /* read alarm status */
  short cnc_alarm2(int FlibHndl, OutObject<Integer> a);

  /* read alarm information */
  short cnc_rdalminfo(int FlibHndl, short a, short b, short c, ALMINFO_1 d);

  short cnc_rdalminfo(int FlibHndl, short a, short b, short c, ALMINFO_2 d);

  /* read alarm message */
  short cnc_rdalmmsg(int FlibHndl, short a, RefObject<Short> b, ODBALMMSG c);

  /* read alarm message(2) */
  short cnc_rdalmmsg2(int FlibHndl, short a, RefObject<Short> b, ODBALMMSG2 c);

  /* clear CNC alarm */
  short cnc_clralm(int FlibHndl, short a);

  /* read modal data */
  short cnc_modal(int FlibHndl, short a, short b, ODBMDL_1 c);

  short cnc_modal(int FlibHndl, short a, short b, ODBMDL_2 c);

  short cnc_modal(int FlibHndl, short a, short b, ODBMDL_3 c);

  short cnc_modal(int FlibHndl, short a, short b, ODBMDL_4 c);

  short cnc_modal(int FlibHndl, short a, short b, ODBMDL_5 c);

  /* read G code */
  short cnc_rdgcode(int FlibHndl, short a, short b, RefObject<Short> c, ODBGCD d);

  /* read command value */
  short cnc_rdcommand(int FlibHndl, short a, short b, RefObject<Short> c, ODBCMD d);

  /* read diagnosis data */
  short cnc_diagnoss(int FlibHndl, short a, short b, short c, ODBDGN_1 d);

  short cnc_diagnoss(int FlibHndl, short a, short b, short c, ODBDGN_2 d);

  short cnc_diagnoss(int FlibHndl, short a, short b, short c, ODBDGN_3 d);

  short cnc_diagnoss(int FlibHndl, short a, short b, short c, ODBDGN_4 d);

  /* read diagnosis data(area specified) */
  short cnc_diagnosr(int FlibHndl, RefObject<Short> a, short b, RefObject<Short> c, RefObject<Short> d, Object e);

  /* read A/D conversion data */
  short cnc_adcnv(int FlibHndl, short a, short b, ODBAD c);

  /* read operator's message */
  short cnc_rdopmsg(int FlibHndl, short a, short b, OPMSG c);

  /* read operator's message */
  short cnc_rdopmsg2(int FlibHndl, short a, short b, OPMSG2 c);

  /* read operator's message */
  short cnc_rdopmsg3(int FlibHndl, short a, RefObject<Short> b, OPMSG3 c);

  /* set path number(for 4 axes lathes, multi-path) */
  short cnc_setpath(int FlibHndl, short a);

  /* get path number(for 4 axes lathes, multi-path) */
  short cnc_getpath(int FlibHndl, OutObject<Short> a, OutObject<Short> b);

  /* allocate library handle */
  short cnc_allclibhndl(OutObject<Short> FlibHndl);

  /* free library handle */
  short cnc_freelibhndl(int FlibHndl);

  /* get library option */
  short cnc_getlibopt(int FlibHndl, int a, Object b, RefObject<Integer> c);

  /* set library option */
  short cnc_setlibopt(int FlibHndl, int a, Object b, int c);

  /* get custom macro type */
  short cnc_getmactype(int FlibHndl, OutObject<Short> a);

  /* set custom macro type */
  short cnc_setmactype(int FlibHndl, short a);

  /* get P code macro type */
  short cnc_getpmactype(int FlibHndl, OutObject<Short> a);

  /* set P code macro type */
  short cnc_setpmactype(int FlibHndl, short a);

  /* get screen status */
  short cnc_getcrntscrn(int FlibHndl, OutObject<Short> a);

  /* change screen mode */
  short cnc_slctscrn(int FlibHndl, short a);

  /* read CNC configuration information */
  short cnc_sysconfig(int FlibHndl, ODBSYSC a);

  /* read program restart information */
  short cnc_rdprstrinfo(int FlibHndl, ODBPRS a);

  /* search sequence number for program restart */
  short cnc_rstrseqsrch(int FlibHndl, int a, int b, short c, short d);

  /* search sequence number for program restart 2 */
  short cnc_rstrseqsrch2(int FlibHndl, int a, int b, short c, short d, int e);

  /* read output signal image of software operator's panel  */
  short cnc_rdopnlsgnl(int FlibHndl, short a, IODBSGNL b);

  /* write output signal of software operator's panel  */
  short cnc_wropnlsgnl(int FlibHndl, IODBSGNL a);

  /* read general signal image of software operator's panel  */
  short cnc_rdopnlgnrl(int FlibHndl, short a, IODBGNRL b);

  /* write general signal image of software operator's panel  */
  short cnc_wropnlgnrl(int FlibHndl, IODBGNRL a);

  /* read general signal name of software operator's panel  */
  short cnc_rdopnlgsname(int FlibHndl, short a, IODBRDNA b);

  /* write general signal name of software operator's panel  */
  short cnc_wropnlgsname(int FlibHndl, IODBRDNA a);

  /* get detail error */
  short cnc_getdtailerr(int FlibHndl, ODBERR a);

  /* read informations of CNC parameter */
  short cnc_rdparainfo(int FlibHndl, short a, short b, ODBPARAIF c);

  /* read informations of CNC setting data */
  short cnc_rdsetinfo(int FlibHndl, short a, short b, ODBSETIF c);

  /* read informations of CNC diagnose data */
  short cnc_rddiaginfo(int FlibHndl, short a, short b, ODBDIAGIF c);

  /* read maximum, minimum and total number of CNC parameter */
  short cnc_rdparanum(int FlibHndl, ODBPARANUM a);

  /* read maximum, minimum and total number of CNC setting data */
  short cnc_rdsetnum(int FlibHndl, ODBSETNUM a);

  /* read maximum, minimum and total number of CNC diagnose data */
  short cnc_rddiagnum(int FlibHndl, ODBDIAGNUM a);

  /* get maximum valid figures and number of decimal places */
  short cnc_getfigure(int FlibHndl, short a, OutObject<Short> b, Object c, Object d);

  /* read F-ROM information on CNC  */
  short cnc_rdfrominfo(int FlibHndl, short a, ODBFINFO b);

  /* start of reading F-ROM data from CNC */
  short cnc_fromsvstart(int FlibHndl, short a, Object b, int c);

  /* read F-ROM data from CNC */
  short cnc_fromsave(int FlibHndl, OutObject<Short> a, Object b, RefObject<Integer> c);

  /* end of reading F-ROM data from CNC */
  short cnc_fromsvend(int FlibHndl);

  /* start of writing F-ROM data to CNC */
  short cnc_fromldstart(int FlibHndl, short a, int b);

  /* write F-ROM data to CNC */
  short cnc_fromload(int FlibHndl, Object a, RefObject<Integer> b);

  /* end of writing F-ROM data to CNC */
  short cnc_fromldend(int FlibHndl);

  /* delete F-ROM data on CNC */
  short cnc_fromdelete(int FlibHndl, short a, Object b, int c);

  /* read S-RAM information on CNC */
  short cnc_rdsraminfo(int FlibHndl, ODBSINFO a);

  /* start of reading S-RAM data from CNC */
  short cnc_srambkstart(int FlibHndl, Object a, int b);

  /* read S-RAM data from CNC */
  short cnc_srambackup(int FlibHndl, OutObject<Short> a, Object b, RefObject<Integer> c);

  /* end of reading S-RAM data from CNC */
  short cnc_srambkend(int FlibHndl);

  /* read F-ROM information on CNC  */
  short cnc_getfrominfo(int FlibHndl, short a, OutObject<Short> b, ODBFINFORM c);

  /* start of reading F-ROM data from CNC */
  short cnc_fromgetstart(int FlibHndl, short a, Object b);

  /* read F-ROM data from CNC */
  short cnc_fromget(int FlibHndl, OutObject<Short> a, Object b, RefObject<Integer> c);

  /* end of reading F-ROM data from CNC */
  short cnc_fromgetend(int FlibHndl);

  /* start of writing F-ROM data to CNC */
  short cnc_fromputstart(int FlibHndl, short a);

  /* write F-ROM data to CNC */
  short cnc_fromput(int FlibHndl, Object a, RefObject<Integer> b);

  /* end of writing F-ROM data to CNC */
  short cnc_fromputend(int FlibHndl);

  /* delete F-ROM data on CNC */
  short cnc_fromremove(int FlibHndl, short a, Object b);

  /* read S-RAM information on CNC */
  short cnc_getsraminfo(int FlibHndl, ODBSINFO a);

  /* start of reading S-RAM data from CNC */
  short cnc_sramgetstart(int FlibHndl, Object a);

  /* start of reading S-RAM data from CNC (2) */
  short cnc_sramgetstart2(int FlibHndl, Object a);

  /* read S-RAM data from CNC */
  short cnc_sramget(int FlibHndl, OutObject<Short> a, Object b, RefObject<Integer> c);

  /* read S-RAM data from CNC (2) */
  short cnc_sramget2(int FlibHndl, OutObject<Short> a, Object b, RefObject<Integer> c);

  /* end of reading S-RAM data from CNC */
  short cnc_sramgetend(int FlibHndl);

  /* end of reading S-RAM data from CNC (2) */
  short cnc_sramgetend2(int FlibHndl);

  /* read number of S-RAM data kind on CNC */
  short cnc_rdsramnum(int FlibHndl, OutObject<Short> a);

  /* read S-RAM data address information on CNC */
  short cnc_rdsramaddr(int FlibHndl, OutObject<Short> a, SRAMADDR b);

  /* get current NC data protection information */
  short cnc_getlockstat(int FlibHndl, short a, OutObject<Byte> b);

  /* change NC data protection status */
  short cnc_chgprotbit(int FlibHndl, short a, RefObject<Byte> b, int c);

  /* transfer a file from host computer to CNC by FTP */
  short cnc_dtsvftpget(int FlibHndl, Object a, Object b);

  /* transfer a file from CNC to host computer by FTP */
  short cnc_dtsvftpput(int FlibHndl, Object a, Object b);

  /* get transfer status for FTP */
  short cnc_dtsvftpstat(int FlibHndl);

  /* read file directory in Data Server */
  short cnc_dtsvrdpgdir(int FlibHndl, Object a, short b, ODBDSDIR c);

  /* delete files in Data Server */
  short cnc_dtsvdelete(int FlibHndl, Object a);

  /* down load from CNC (transfer a file from CNC to MMC) */
  short cnc_dtsvdownload(int FlibHndl, Object a);

  /* up load to CNC (transfer a file from MMC to CNC) */
  short cnc_dtsvupload(int FlibHndl, Object a);

  /* close upload/download between Data Server and CNC */
  short cnc_dtsvcnclupdn(int FlibHndl);

  /* get transfer status for up/down load */
  short cnc_dtsvupdnstat(int FlibHndl);

  /* get file name for DNC operation in Data Server */
  short cnc_dtsvgetdncpg(int FlibHndl, Object a);

  /* set program number of DNC oparation to CNC */
  short cnc_dtsvsetdncpg(int FlibHndl, Object a);

  /* read setting data for Data Server */
  short cnc_dtsvrdset(int FlibHndl, IODBDSSET a);

  /* write setting data for Data Server */
  short cnc_dtsvwrset(int FlibHndl, IODBDSSET a);

  /* check hard disk in Data Server */
  short cnc_dtsvchkdsk(int FlibHndl);

  /* format hard disk in Data Server */
  short cnc_dtsvhdformat(int FlibHndl);

  /* save interface area in Data Server */
  short cnc_dtsvsavecram(int FlibHndl);

  /* get interface area in Data Server */
  short cnc_dtsvrdcram(int FlibHndl, int a, RefObject<Integer> b, Object c);

  /* read maintenance information for Data Server */
  short cnc_dtsvmntinfo(int FlibHndl, ODBDSMNT a);

  /* get Data Server mode */
  short cnc_dtsvgetmode(int FlibHndl, OutObject<Short> a);

  /* set Data Server mode */
  short cnc_dtsvsetmode(int FlibHndl, short a);

  /* read error message for Data Server */
  short cnc_dtsvrderrmsg(int FlibHndl, short a, Object b);

  /* transfar file from Pc to Data Server */
  short cnc_dtsvwrfile(int FlibHndl, Object a, Object b, short c);

  /* transfar file from Data Server to Pc */
  short cnc_dtsvrdfile(int FlibHndl, Object a, Object b, short c);

  /* read the loop gain for each axis */
  short cnc_rdloopgain(int FlibHndl, OutObject<Integer> a);

  /* read the actual current for each axis */
  short cnc_rdcurrent(int FlibHndl, OutObject<Short> a);

  /* read the actual speed for each axis */
  short cnc_rdsrvspeed(int FlibHndl, OutObject<Integer> a);

  /* read the operation mode */
  short cnc_rdopmode(int FlibHndl, OutObject<Short> a);

  /* read the position deviation S */
  short cnc_rdposerrs(int FlibHndl, OutObject<Integer> a);

  /* read the position deviation S1 and S2 */
  short cnc_rdposerrs2(int FlibHndl, ODBPSER a);

  /* read the position deviation Z in the rigid tap mode */
  short cnc_rdposerrz(int FlibHndl, OutObject<Integer> a);

  /* read the synchronous error in the synchronous control mode */
  short cnc_rdsynerrsy(int FlibHndl, OutObject<Integer> a);

  /* read the synchronous error in the rigid tap mode */
  short cnc_rdsynerrrg(int FlibHndl, OutObject<Integer> a);

  /* read the spindle alarm */
  short cnc_rdspdlalm(int FlibHndl, Object a);

  /* read the control input signal */
  short cnc_rdctrldi(int FlibHndl, ODBSPDI a);

  /* read the control output signal */
  short cnc_rdctrldo(int FlibHndl, ODBSPDO a);

  /* read the number of controled spindle */
  short cnc_rdnspdl(int FlibHndl, OutObject<Short> a);

  /* read data from FANUC BUS */
  short cnc_rdfbusmem(int FlibHndl, short a, short b, int c, int d, Object e);

  /* write data to FANUC BUS */
  short cnc_wrfbusmem(int FlibHndl, short a, short b, int c, int d, Object e);

  /* read the parameter of wave diagnosis */
  short cnc_rdwaveprm(int FlibHndl, IODBWAVE a);

  /* write the parameter of wave diagnosis */
  short cnc_wrwaveprm(int FlibHndl, IODBWAVE a);

  /* read the parameter of wave diagnosis 2 */
  short cnc_rdwaveprm2(int FlibHndl, IODBWVPRM a);

  /* write the parameter of wave diagnosis 2 */
  short cnc_wrwaveprm2(int FlibHndl, IODBWVPRM a);

  /* start the sampling for wave diagnosis */
  short cnc_wavestart(int FlibHndl);

  /* stop the sampling for wave diagnosis */
  short cnc_wavestop(int FlibHndl);

  /* read the status of wave diagnosis */
  short cnc_wavestat(int FlibHndl, OutObject<Short> a);

  /* read the data of wave diagnosis */
  short cnc_rdwavedata(int FlibHndl, short a, short b, int c, RefObject<Integer> d, ODBWVDT e);

  /* read the parameter of wave diagnosis for remort diagnosis */
  short cnc_rdrmtwaveprm(int FlibHndl, IODBRMTPRM a, short b);

  /* write the parameter of wave diagnosis for remort diagnosis */
  short cnc_wrrmtwaveprm(int FlibHndl, IODBRMTPRM a);

  /* start the sampling for wave diagnosis for remort diagnosis */
  short cnc_rmtwavestart(int FlibHndl);

  /* stop the sampling for wave diagnosis for remort diagnosis */
  short cnc_rmtwavestop(int FlibHndl);

  /* read the status of wave diagnosis for remort diagnosis*/
  short cnc_rmtwavestat(int FlibHndl, OutObject<Short> a);

  /* read the data of wave diagnosis for remort diagnosis */
  short cnc_rdrmtwavedt(int FlibHndl, short a, int b, RefObject<Integer> c, ODBRMTDT d);

  /* read of address for PMC signal batch save */
  short cnc_rdsavsigadr(int FlibHndl, IODBSIGAD a, short b);

  /* write of address for PMC signal batch save */
  short cnc_wrsavsigadr(int FlibHndl, IODBSIGAD a, OutObject<Short> b);

  /* read of data for PMC signal batch save */
  short cnc_rdsavsigdata(int FlibHndl, short a, short b, Object c, RefObject<Short> d);

  /* read M-code group data */
  short cnc_rdmgrpdata(int FlibHndl, short a, RefObject<Short> b, ODBMGRP c);

  /* write M-code group data */
  short cnc_wrmgrpdata(int FlibHndl, IDBMGRP a);

  /* read executing M-code group data */
  short cnc_rdexecmcode(int FlibHndl, short a, RefObject<Short> b, ODBEXEM c);

  /* read program restart M-code group data */
  short cnc_rdrstrmcode(int FlibHndl, short a, RefObject<Short> b, ODBRSTRM c);

  /* read processing time stamp data */
  short cnc_rdproctime(int FlibHndl, ODBPTIME a);

  /* read MDI program stat */
  short cnc_rdmdiprgstat(int FlibHndl, OutObject<Short> a);

  /* read program directory for processing time data */
  short cnc_rdprgdirtime(int FlibHndl, RefObject<Integer> a, RefObject<Short> b, PRGDIRTM c);

  /* read program directory 2 */
//#if (!ONO8D)
  short cnc_rdprogdir2(int FlibHndl, short a, RefObject<Short> b, RefObject<Short> c, PRGDIR2 d);

  //#else
  /* short cnc_rdprogdir2(int FlibHndl, short a, RefObject<Short> b, RefObject<Short> c, PRGDIR2 d);*/
//#endif
  /* read program directory 3 */
  short cnc_rdprogdir3(int FlibHndl, short a, RefObject<Integer> b, RefObject<Short> c, PRGDIR3 d);

  /* read program directory 4 */
  short cnc_rdprogdir4(int FlibHndl, short a, int b, RefObject<Short> c, PRGDIR4 d);

  /* read DNC file name for DNC1, DNC2, OSI-Ethernet */
  short cnc_rddncfname(int FlibHndl, Object a);

  /* write DNC file name for DNC1, DNC2, OSI-Ethernet */
  short cnc_wrdncfname(int FlibHndl, Object a);

  /* read communication parameter for DNC1, DNC2, OSI-Ethernet */
  short cnc_rdcomparam(int FlibHndl, IODBCPRM a);

  /* write communication parameter for DNC1, DNC2, OSI-Ethernet */
  short cnc_wrcomparam(int FlibHndl, IODBCPRM a);

  /* read log message for DNC2 */
  short cnc_rdcomlogmsg(int FlibHndl, Object a);

  /* read operator message for DNC1, DNC2 */
  short cnc_rdcomopemsg(int FlibHndl, Object a);

  /* read recieve message for OSI-Ethernet */
  short cnc_rdrcvmsg(int FlibHndl, Object a);

  /* read send message for OSI-Ethernet */
  short cnc_rdsndmsg(int FlibHndl, Object a);

  /* send message for OSI-Ethernet */
  short cnc_sendmessage(int FlibHndl, Object a);

  /* clear message buffer for OSI-Ethernet */
  short cnc_clrmsgbuff(int FlibHndl, short a);

  /* read message recieve status for OSI-Ethernet */
  short cnc_rdrcvstat(int FlibHndl, OutObject<Short> a);

  /* read interference check */
  short cnc_rdintchk(int FlibHndl, short a, short b, short c, short d, IODBINT e);

  /* write interference check */
  short cnc_wrintchk(int FlibHndl, short a, IODBINT b);

  /* read interference check information */
  short cnc_rdintinfo(int FlibHndl, OutObject<Short> a);

  /* read work coordinate shift */
  short cnc_rdwkcdshft(int FlibHndl, short a, short b, IODBWCSF c);

  /* write work coordinate shift */
  short cnc_wrwkcdshft(int FlibHndl, short a, IODBWCSF b);

  /* read work coordinate shift measure */
  short cnc_rdwkcdsfms(int FlibHndl, short a, short b, IODBWCSF c);

  /* write work coordinate shift measure */
  short cnc_wrwkcdsfms(int FlibHndl, short a, IODBWCSF b);

  /* stop the sampling for operator message history */
  short cnc_stopomhis(int FlibHndl);

  /* start the sampling for operator message history */
  short cnc_startomhis(int FlibHndl);

  /* read operator message history information */
  short cnc_rdomhisinfo(int FlibHndl, ODBOMIF a);

  /* read operator message history */
  short cnc_rdomhistry(int FlibHndl, short a, RefObject<Short> b, ODBOMHIS c);

  /* read operater message history data F30i */
  short cnc_rdomhistry2(int FlibHndl, short a, short b, short c, ODBOMHIS2 d);

  /* write external key operation history for F30i*/
  short cnc_wrkeyhistry(int FlibHndl, byte a);

  /* clear operator message history */
  short cnc_clearomhis(int FlibHndl);

  /* read b-axis tool offset value(area specified) */
  short cnc_rdbtofsr(int FlibHndl, short a, short b, short c, short d, IODBBTO e);

  /* write b-axis tool offset value(area specified) */
  short cnc_wrbtofsr(int FlibHndl, short a, IODBBTO b);

  /* read b-axis tool offset information */
  short cnc_rdbtofsinfo(int FlibHndl, ODBBTLINF a);

  /* read b-axis command */
  short cnc_rdbaxis(int FlibHndl, ODBBAXIS a);

  /* read CNC system soft series and version */
  short cnc_rdsyssoft(int FlibHndl, ODBSYSS a);

  /* read CNC system soft series and version (2) */
  short cnc_rdsyssoft2(int FlibHndl, ODBSYSS2 a);

  /* read CNC module configuration information */
  short cnc_rdmdlconfig(int FlibHndl, ODBMDLC a);

  /* read CNC module configuration information 2 */
  short cnc_rdmdlconfig2(int FlibHndl, Object a);

  /* read processing condition file (processing data) */
  short cnc_rdpscdproc(int FlibHndl, short a, RefObject<Short> b, IODBPSCD c);

  /* write processing condition file (processing data) */
  short cnc_wrpscdproc(int FlibHndl, short a, RefObject<Short> b, IODBPSCD c);

  /* read processing condition file (piercing data) */
  short cnc_rdpscdpirc(int FlibHndl, short a, RefObject<Short> b, IODBPIRC c);

  /* write processing condition file (piercing data) */
  short cnc_wrpscdpirc(int FlibHndl, short a, RefObject<Short> b, IODBPIRC c);

  /* read processing condition file (edging data) */
  short cnc_rdpscdedge(int FlibHndl, short a, RefObject<Short> b, IODBEDGE c);

  /* write processing condition file (edging data) */
  short cnc_wrpscdedge(int FlibHndl, short a, RefObject<Short> b, IODBEDGE c);

  /* read processing condition file (slope data) */
  short cnc_rdpscdslop(int FlibHndl, short a, RefObject<Short> b, IODBSLOP c);

  /* write processing condition file (slope data) */
  short cnc_wrpscdslop(int FlibHndl, short a, RefObject<Short> b, IODBSLOP c);

  /* read power controll duty data */
  short cnc_rdlpwrdty(int FlibHndl, IODBLPWDT a);

  /* write power controll duty data */
  short cnc_wrlpwrdty(int FlibHndl, IODBLPWDT a);

  /* read laser power data */
  short cnc_rdlpwrdat(int FlibHndl, ODBLOPDT a);

  /* read power complement */
  short cnc_rdlpwrcpst(int FlibHndl, OutObject<Short> a);

  /* write power complement */
  short cnc_wrlpwrcpst(int FlibHndl, short a);

  /* read laser assist gas selection */
  short cnc_rdlagslt(int FlibHndl, IODBLAGSL a);

  /* write laser assist gas selection */
  short cnc_wrlagslt(int FlibHndl, IODBLAGSL a);

  /* read laser assist gas flow */
  short cnc_rdlagst(int FlibHndl, IODBLAGST a);

  /* write laser assist gas flow */
  short cnc_wrlagst(int FlibHndl, IODBLAGST a);

  /* read laser power for edge processing */
  short cnc_rdledgprc(int FlibHndl, IODBLEGPR a);

  /* write laser power for edge processing */
  short cnc_wrledgprc(int FlibHndl, IODBLEGPR a);

  /* read laser power for piercing */
  short cnc_rdlprcprc(int FlibHndl, IODBLPCPR a);

  /* write laser power for piercing */
  short cnc_wrlprcprc(int FlibHndl, IODBLPCPR a);

  /* read laser command data */
  short cnc_rdlcmddat(int FlibHndl, ODBLCMDT a);

  /* read displacement */
  short cnc_rdldsplc(int FlibHndl, OutObject<Short> a);

  /* write displacement */
  short cnc_wrldsplc(int FlibHndl, short a);

  /* read error for axis z */
  short cnc_rdlerrz(int FlibHndl, OutObject<Short> a);

  /* read active number */
  short cnc_rdlactnum(int FlibHndl, ODBLACTN a);

  /* read laser comment */
  short cnc_rdlcmmt(int FlibHndl, ODBLCMMT a);

  /* read laser power select */
  short cnc_rdlpwrslt(int FlibHndl, OutObject<Short> a);

  /* write laser power select */
  short cnc_wrlpwrslt(int FlibHndl, short a);

  /* read laser power controll */
  short cnc_rdlpwrctrl(int FlibHndl, OutObject<Short> a);

  /* write laser power controll */
  short cnc_wrlpwrctrl(int FlibHndl, short a);

  /* read power correction factor history data */
  short cnc_rdpwofsthis(int FlibHndl, int a, RefObject<Integer> b, ODBPWOFST c);

  /* read management time */
  short cnc_rdmngtime(int FlibHndl, int a, RefObject<Integer> b, IODBMNGTIME c);

  /* write management time */
  short cnc_wrmngtime(int FlibHndl, int a, IODBMNGTIME b);

  /* read data related to electrical discharge at power correction ends */
  short cnc_rddischarge(int FlibHndl, ODBDISCHRG a);

  /* read alarm history data related to electrical discharg */
  short cnc_rddischrgalm(int FlibHndl, int a, RefObject<Integer> b, ODBDISCHRGALM c);

  /* get date and time from cnc */
  short cnc_gettimer(int FlibHndl, IODBTIMER a);

  /* set date and time for cnc */
  short cnc_settimer(int FlibHndl, IODBTIMER a);

  /* read timer data from cnc */
  short cnc_rdtimer(int FlibHndl, short a, IODBTIME b);

  /* write timer data for cnc */
  short cnc_wrtimer(int FlibHndl, short a, IODBTIME b);

  /* read tool controll data */
  short cnc_rdtlctldata(int FlibHndl, IODBTLCTL a);

  /* write tool controll data */
  short cnc_wrtlctldata(int FlibHndl, IODBTLCTL a);

  /* read tool data */
  short cnc_rdtooldata(int FlibHndl, short a, RefObject<Short> b, IODBTLDT c);

  /* read tool data */
  short cnc_wrtooldata(int FlibHndl, short a, RefObject<Short> b, IODBTLDT c);

  /* read multi tool data */
  short cnc_rdmultitldt(int FlibHndl, short a, RefObject<Short> b, IODBMLTTL c);

  /* write multi tool data */
  short cnc_wrmultitldt(int FlibHndl, short a, RefObject<Short> b, IODBMLTTL c);

  /* read multi tap data */
  short cnc_rdmtapdata(int FlibHndl, short a, RefObject<Short> b, IODBMTAP c);

  /* write multi tap data */
  short cnc_wrmtapdata(int FlibHndl, short a, RefObject<Short> b, IODBMTAP c);

  /* read multi-piece machining number */
  short cnc_rdmultipieceno(int FlibHndl, OutObject<Integer> a);

  /* read tool information */
  short cnc_rdtoolinfo(int FlibHndl, ODBPTLINF a);

  /* read safetyzone data */
  short cnc_rdsafetyzone(int FlibHndl, short a, RefObject<Short> b, IODBSAFE c);

  /* write safetyzone data */
  short cnc_wrsafetyzone(int FlibHndl, short a, RefObject<Short> b, IODBSAFE c);

  /* read toolzone data */
  short cnc_rdtoolzone(int FlibHndl, short a, RefObject<Short> b, IODBTLZN c);

  /* write toolzone data */
  short cnc_wrtoolzone(int FlibHndl, short a, RefObject<Short> b, IODBTLZN c);

  /* read active toolzone data */
  short cnc_rdacttlzone(int FlibHndl, ODBACTTLZN a);

  /* read setzone number */
  short cnc_rdsetzone(int FlibHndl, OutObject<Short> a);

  /* write setzone number */
  short cnc_wrsetzone(int FlibHndl, short a);

  /* read block restart information */
  short cnc_rdbrstrinfo(int FlibHndl, ODBBRS a);

  /* read menu switch signal */
  short cnc_rdmenuswitch(int FlibHndl, OutObject<Short> a);

  /* write menu switch signal */
  short cnc_wrmenuswitch(int FlibHndl, short a, short b);

  /* read tool radius offset for position data */
  short cnc_rdradofs(int FlibHndl, ODBROFS a);

  /* read tool length offset for position data */
  short cnc_rdlenofs(int FlibHndl, ODBLOFS a);

  /* read fixed cycle for position data */
  short cnc_rdfixcycle(int FlibHndl, ODBFIX a);

  /* read coordinate rotate for position data */
  short cnc_rdcdrotate(int FlibHndl, ODBROT a);

  /* read 3D coordinate convert for position data */
  short cnc_rd3dcdcnv(int FlibHndl, ODB3DCD a);

  /* read programable mirror image for position data */
  short cnc_rdmirimage(int FlibHndl, ODBMIR a);

  /* read scaling for position data */
  short cnc_rdscaling(int FlibHndl, ODBSCL a);

  /* read 3D tool offset for position data */
  short cnc_rd3dtofs(int FlibHndl, ODB3DTO a);

  /* read tool position offset for position data */
  short cnc_rdposofs(int FlibHndl, ODBPOFS a);

  /* read hpcc setting data */
  short cnc_rdhpccset(int FlibHndl, IODBHPST a);

  /* write hpcc setting data */
  short cnc_wrhpccset(int FlibHndl, IODBHPST a);

  /* hpcc data auto setting data */
  short cnc_hpccatset(int FlibHndl);

  /* read hpcc tuning data ( parameter input ) */
  short cnc_rdhpcctupr(int FlibHndl, IODBHPPR a);

  /* write hpcc tuning data ( parameter input ) */
  short cnc_wrhpcctupr(int FlibHndl, IODBHPPR a);

  /* read hpcc tuning data ( acc input ) */
  short cnc_rdhpcctuac(int FlibHndl, IODBHPAC a);

  /* write hpcc tuning data ( acc input ) */
  short cnc_wrhpcctuac(int FlibHndl, IODBHPAC a);

  /* hpcc data auto tuning */
  short cnc_hpccattune(int FlibHndl, short a, OutObject<Short> b);

  /* read hpcc fine level */
  short cnc_hpccactfine(int FlibHndl, OutObject<Short> a);

  /* select hpcc fine level */
  short cnc_hpccselfine(int FlibHndl, short a);

  /* read active fixture offset */
  short cnc_rdactfixofs(int FlibHndl, short a, IODBZOFS b);

  /* read fixture offset */
  short cnc_rdfixofs(int FlibHndl, short a, short b, short c, short d, IODBZOR e);

  /* write fixture offset */
  short cnc_wrfixofs(int FlibHndl, short a, IODBZOR b);

  /* read tip of tool for 3D handle */
  short cnc_rd3dtooltip(int FlibHndl, ODB3DHDL a);

  /* read pulse for 3D handle */
  short cnc_rd3dpulse(int FlibHndl, ODB3DPLS a);

  /* read move overrlap of tool for 3D handle */
  short cnc_rd3dmovrlap(int FlibHndl, ODB3DHDL a);

  /* read change offset for 3D handle */
  short cnc_rd3dofschg(int FlibHndl, RefObject<Integer> a);

  /* clear pulse and change offset for 3D handle */
  short cnc_clr3dplsmov(int FlibHndl, short a);

  /* cycle start */
  short cnc_start(int FlibHndl);

  /* reset CNC */
  short cnc_reset(int FlibHndl);

  /* reset CNC 2 */
  short cnc_reset2(int FlibHndl);

  /* read axis name */
  short cnc_rdaxisname(int FlibHndl, RefObject<Short> a, ODBAXISNAME b);

  /* read spindle name */
  short cnc_rdspdlname(int FlibHndl, RefObject<Short> a, ODBSPDLNAME b);

  /* read extended axis name */
  short cnc_exaxisname(int FlibHndl, short a, RefObject<Short> b, ODBEXAXISNAME c);

  /* read SRAM variable area for C language executor */
  short cnc_rdcexesram(int FlibHndl, int a, Object b, RefObject<Integer> c);

  /* write SRAM variable area for C language executor */
  short cnc_wrcexesram(int FlibHndl, int a, Object b, RefObject<Integer> c);

  /* read maximum size and linear address of SRAM variable area for C language executor */
  short cnc_cexesraminfo(int FlibHndl, OutObject<Short> a, OutObject<Integer> b, OutObject<Integer> c);

  /* read maximum size of SRAM variable area for C language executor */
  short cnc_cexesramsize(int FlibHndl, OutObject<Integer> a);

  /* read additional workpiece coordinate systems number */
  short cnc_rdcoordnum(int FlibHndl, OutObject<Short> a);

  /* converts from FANUC code to Shift JIS code */
  short cnc_ftosjis(int FlibHndl, Object a, Object b);

  /* Set the unsolicited message parameters */
  short cnc_wrunsolicprm(int FlibHndl, short a, IODBUNSOLIC b);

  /* Get the unsolicited message parameters */
  short cnc_rdunsolicprm(int FlibHndl, short a, IODBUNSOLIC b);

  /* Start of unsolicited message */
  short cnc_unsolicstart(int FlibHndl, short a, int hWnd, int c, short d, OutObject<Short> e);

  /* End of unsolicited message */
  short cnc_unsolicstop(int FlibHndl, short a);

  /* Reads the unsolicited message data */
  short cnc_rdunsolicmsg(short a, IDBUNSOLICMSG b);

  /* read machine specific maintenance item */
  short cnc_rdpm_mcnitem(int FlibHndl, short a, RefObject<Short> b, IODBITEM c);

  /* write machine specific maintenance item */
  short cnc_wrpm_mcnitem(int FlibHndl, short a, short b, IODBITEM c);

  /* read cnc maintenance item */
  short cnc_rdpm_cncitem(int FlibHndl, short a, RefObject<Short> b, IODBITEM c);

  /* read maintenance item status */
  short cnc_rdpm_item(int FlibHndl, short a, RefObject<Short> b, IODBPMAINTE c);

  /* write maintenance item status */
  short cnc_wrpm_item(int FlibHndl, short a, short b, short c, IODBPMAINTE d);

  /* Display of optional message */
  short cnc_dispoptmsg(int FlibHndl, Object a);

  /* Reading of answer for optional message display */
  short cnc_optmsgans(int FlibHndl, OutObject<Short> a);

  /* Get CNC Model */
  short cnc_getcncmodel(int FlibHndl, OutObject<Short> a);

  /* read number of repeats */
  short cnc_rdrepeatval(int FlibHndl, OutObject<Integer> a);

  /* read CNC system hard info */
  short cnc_rdsyshard(int FlibHndl, short a, RefObject<Short> b, ODBSYSH c);

  /* read CNC system soft series and version (3) */
  short cnc_rdsyssoft3(int FlibHndl, short a, RefObject<Short> b, OutObject<Short> c, ODBSYSS3 d);

  /* read digit of program number */
  short cnc_progdigit(int FlibHndl, OutObject<Short> a);

  /* read CNC system path information */
  short cnc_sysinfo_ex(int FlibHndl, ODBSYSEX a);

  /*------------------*/
  /* CNC : SERCOS I/F */
  /*------------------*/
  /* Get reservation of service channel for SERCOS I/F */
  short cnc_srcsrsvchnl(int FlibHndl);

  /* Read ID information of SERCOS I/F */
  short cnc_srcsrdidinfo(int FlibHndl, int a, short b, short c, IODBIDINF d);

  /* Write ID information of SERCOS I/F */
  short cnc_srcswridinfo(int FlibHndl, IODBIDINF a);

  /* Start of reading operation data from drive of SERCOS I/F */
  short cnc_srcsstartrd(int FlibHndl, int a, short b);

  /* Start of writing operation data to drive of SERCOS I/F */
  short cnc_srcsstartwrt(int FlibHndl, int a, short b);

  /* Stop of reading/writing operation data from/to drive of SERCOS I/F */
  short cnc_srcsstopexec(int FlibHndl);

  /* Get execution status of reading/writing operation data of SERCOS I/F */
  short cnc_srcsrdexstat(int FlibHndl, ODBSRCSST a);

  /* Read operation data from data buffer for SERCOS I/F */
  short cnc_srcsrdopdata(int FlibHndl, int a, RefObject<Integer> b, Object c);

  /* Write operation data to data buffer for SERCOS I/F */
  short cnc_srcswropdata(int FlibHndl, int a, int b, Object c);

  /* Free reservation of service channel for SERCOS I/F */
  short cnc_srcsfreechnl(int FlibHndl);

  /* Read drive assign of SERCOS I/F */
  short cnc_srcsrdlayout(int FlibHndl, ODBSRCSLYT a);

  /* Read communication phase of drive of SERCOS I/F */
  short cnc_srcsrddrvcp(int FlibHndl, OutObject<Short> a);

  /*----------------------------*/
  /* CNC : Graphic command data */
  /*----------------------------*/
  /* Start drawing position */
  short cnc_startdrawpos(int FlibHndl);

  /* Stop drawing position */
  short cnc_stopdrawpos(int FlibHndl);

  /* Start dynamic graphic */
  short cnc_startdyngrph(int FlibHndl);

  /* Stop dynamic graphic */
  short cnc_stopdyngrph(int FlibHndl);

  /* Read graphic command data */
  short cnc_rdgrphcmd(int FlibHndl, RefObject<Short> a, Object b);

  /* Update graphic command read pointer */
  short cnc_wrgrphcmdptr(int FlibHndl, short a);

  /* Read cancel flag */
  short cnc_rdgrphcanflg(int FlibHndl, OutObject<Short> a);

  /* Clear graphic command */
  short cnc_clrgrphcmd(int FlibHndl);

  /*---------------------------*/
  /* CNC : Servo learning data */
  /*---------------------------*/
  /* Servo learning data read start */
  short cnc_svdtstartrd(int FlibHndl, short a);

  /* Servo learning data write start */
  short cnc_svdtstartwr(int FlibHndl, short a);

  /* Servo learning data read end */
  short cnc_svdtendrd(int FlibHndl);

  /* Servo learning data write end */
  short cnc_svdtendwr(int FlibHndl);

  /* Servo learning data read/write stop */
  short cnc_svdtstopexec(int FlibHndl);

  /* Servo learning data read from I/F buffer */
  short cnc_svdtrddata(int FlibHndl, OutObject<Short> a, RefObject<Integer> b, Object c);

  /* Servo learning data write to I/F buffer */
  short cnc_svdtwrdata(int FlibHndl, OutObject<Short> a, RefObject<Integer> b, Object c);

  /*----------------------------*/
  /* CNC : Servo Guide          */
  /*----------------------------*/
  /* Servo Guide (Channel data set) */
  short cnc_sdsetchnl(int FlibHndl, short a, IDBCHAN b);

  /* Servo Guide (Channel data clear) */
  short cnc_sdclrchnl(int FlibHndl);

  /* Servo Guide (Sampling start) */
  short cnc_sdstartsmpl(int FlibHndl, short a, int b, Object c);

  /* Servo Guide (Sampling cancel) */
  short cnc_sdcancelsmpl(int FlibHndl);

  /* Servo Guide (read Sampling data) */
  short cnc_sdreadsmpl(int FlibHndl, OutObject<Short> a, int b, ODBSD c);

  /* Servo Guide (Sampling end) */
  short cnc_sdendsmpl(int FlibHndl);

  /* Servo Guide (read 1 shot data) */
  short cnc_sdread1shot(int FlibHndl, Object a);

  /* Servo feedback data (Channel data set) */
  short cnc_sfbsetchnl(int FlibHndl, short a, int b, IDBSFBCHAN c);

  /* Servo feedback data (Channel data clear) */
  short cnc_sfbclrchnl(int FlibHndl);

  /* Servo feedback data (Sampling start) */
  short cnc_sfbstartsmpl(int FlibHndl, short a, int b);

  /* Servo feedback data (Sampling cancel) */
  short cnc_sfbcancelsmpl(int FlibHndl);

  /* Servo feedback data (read Sampling data) */
  short cnc_sfbreadsmpl(int FlibHndl, OutObject<Short> a, int b, ODBSD c);

  /* Servo feedback data (Sampling end) */
  short cnc_sfbendsmpl(int FlibHndl);

  /*----------------------------*/
  /* CNC : NC display function  */
  /*----------------------------*/
  /* Start NC display */
  short cnc_startnccmd(int FlibHndl);

  /* Start NC display (2) */
  short cnc_startnccmd2(int FlibHndl, Object a);

  /* Stop NC display */
  short cnc_stopnccmd(int FlibHndl);

  /* Get NC display mode */
  short cnc_getdspmode(int FlibHndl, OutObject<Short> a);

  /*------------------------------------*/
  /* CNC : Remote diagnostics function  */
  /*------------------------------------*/
  /* Start remote diagnostics function */
  short cnc_startrmtdgn(int FlibHndl);

  /* Stop remote diagnostics function */
  short cnc_stoprmtdgn(int FlibHndl);

  /* Read data from remote diagnostics I/F */
  short cnc_rdrmtdgn(int FlibHndl, OutObject<Integer> a, Object b);

  /* Write data to remote diagnostics I/F */
  short cnc_wrrmtdgn(int FlibHndl, RefObject<Integer> a, Object b);

  /* Set CommStatus of remote diagnostics I/F area */
  short cnc_wrcommstatus(int FlibHndl, short a);

  /* Check remote diagnostics I/F */
  short cnc_chkrmtdgn(int FlibHndl);

  /*-------------------------*/
  /* CNC : FS18-LN function  */
  /*-------------------------*/
  /* read allowance */
  short cnc_allowance(int FlibHndl, short a, short b, ODBAXIS c);

  /* read allowanced state */
  short cnc_allowcnd(int FlibHndl, short a, short b, ODBCAXIS c);

  /* set work zero */
  short cnc_workzero(int FlibHndl, short a, IODBZOFS b);

  /* set slide position */
  short cnc_slide(int FlibHndl, short a, short b, ODBAXIS c);

  /*----------------------------------*/
  /* CNC: Teaching data I/F function  */
  /*----------------------------------*/
  /* Teaching data get start */
  short cnc_startgetdgdat(int FlibHndl);

  /* Teaching data get stop */
  short cnc_stopgetdgdat(int FlibHndl);

  /* Teaching data read */
  short cnc_rddgdat(int FlibHndl, RefObject<Short> a, Object b);

  /* Teaching data read pointer write */
  short cnc_wrdgdatptr(int FlibHndl, short a);

  /* Teaching data clear */
  short cnc_clrdgdat(int FlibHndl);

  /*---------------------------------*/
  /* CNC : C-EXE SRAM file function  */
  /*---------------------------------*/
  /* open C-EXE SRAM file */
  short cnc_opencexefile(int FlibHndl, Object a, short b, short c);

  /* close C-EXE SRAM file */
  short cnc_closecexefile(int FlibHndl);

  /* read C-EXE SRAM file */
  short cnc_rdcexefile(int FlibHndl, Object a, RefObject<Integer> b);

  /* write C-EXE SRAM file */
  short cnc_wrcexefile(int FlibHndl, Object a, RefObject<Integer> b);

  /* read C-EXE SRAM disk directory */
  short cnc_cexedirectory(int FlibHndl, Object a, RefObject<Short> b, short c, CFILEINFO d);

  /*-----*/
  /* PMC */
  /*-----*/
  /* read message from PMC to MMC */
  short pmc_rdmsg(int FlibHndl, RefObject<Short> a, Object b);

  /* write message from MMC to PMC */
  short pmc_wrmsg(int FlibHndl, short a, Object b);

  /* read message from PMC to MMC(conditional) */
  short pmc_crdmsg(int FlibHndl, RefObject<Short> a, Object b);

  /* write message from MMC to PMC(conditional) */
  short pmc_cwrmsg(int FlibHndl, short a, Object b);

  /* read PMC data(area specified) */
  short pmc_rdpmcrng(int FlibHndl, short a, short b, short c, short d, short e, IODBPMC0 f);

  short pmc_rdpmcrng(int FlibHndl, short a, short b, short c, short d, short e, IODBPMC1 f);

  short pmc_rdpmcrng(int FlibHndl, short a, short b, short c, short d, short e, IODBPMC2 f);

  /* write PMC data(area specified) */
  short pmc_wrpmcrng(int FlibHndl, short a, IODBPMC0 b);

  short pmc_wrpmcrng(int FlibHndl, short a, IODBPMC1 b);

  short pmc_wrpmcrng(int FlibHndl, short a, IODBPMC2 b);

  /* read data from extended backup memory */
  short pmc_rdkpm(int FlibHndl, int a, Object b, short c);

  /* write data to extended backup memory */
  short pmc_wrkpm(int FlibHndl, int a, Object b, short c);

  /* read data from extended backup memory 2 */
  short pmc_rdkpm2(int FlibHndl, int a, Object b, int c);

  /* write data to extended backup memory 2 */
  short pmc_wrkpm2(int FlibHndl, int a, Object b, int c);

  /* read maximum size of extended backup memory */
  short pmc_kpmsiz(int FlibHndl, OutObject<Integer> a);

  /* read informations of PMC data */
  short pmc_rdpmcinfo(int FlibHndl, short a, ODBPMCINF b);

  /* read PMC parameter data table contorol data */
  short pmc_rdcntldata(int FlibHndl, short a, short b, short c, IODBPMCCNTL d);

  /* write PMC parameter data table contorol data */
  short pmc_wrcntldata(int FlibHndl, short a, IODBPMCCNTL b);

  /* read PMC parameter data table contorol data group number */
  short pmc_rdcntlgrp(int FlibHndl, OutObject<Short> a);

  /* write PMC parameter data table contorol data group number */
  short pmc_wrcntlgrp(int FlibHndl, short a);

  /* read PMC alarm message */
  short pmc_rdalmmsg(int FlibHndl, short a, RefObject<Short> b, OutObject<Short> c, ODBPMCALM d);

  /* get detail error for pmc */
  short pmc_getdtailerr(int FlibHndl, ODBPMCERR a);

  /* read PMC memory data */
  short pmc_rdpmcmem(int FlibHndl, short a, int b, int c, Object d);

  /* write PMC memory data */
  short pmc_wrpmcmem(int FlibHndl, short a, int b, int c, Object d);

  /* read PMC-SE memory data */
  short pmc_rdpmcsemem(int FlibHndl, short a, int b, int c, Object d);

  /* write PMC-SE memory data */
  short pmc_wrpmcsemem(int FlibHndl, short a, int b, int c, Object d);

  /* read pmc title data */
  short pmc_rdpmctitle(int FlibHndl, ODBPMCTITLE a);

  /* read PMC parameter start */
  short pmc_rdprmstart(int FlibHndl);

  /* read PMC parameter */
  short pmc_rdpmcparam(int FlibHndl, RefObject<Integer> a, Object b);

  /* read PMC parameter end */
  short pmc_rdprmend(int FlibHndl);

  /* write PMC parameter start */
  short pmc_wrprmstart(int FlibHndl);

  /* write PMC parameter */
  short pmc_wrpmcparam(int FlibHndl, RefObject<Integer> a, Object b);

  /* write PMC parameter end */
  short pmc_wrprmend(int FlibHndl);

  /* read PMC data */
  short pmc_rdpmcrng_ext(int FlibHndl, short a, IODBPMCEXT b);

  /* write PMC I/O link assigned data */
  short pmc_wriolinkdat(int FlibHndl, int a, Object b, int c);

  /* read PMC address information */
  short pmc_rdpmcaddr(int FlibHndl, ODBPMCADR a);

  /* select PMC unit */
  short pmc_select_pmc_unit(int FlibHndl, int a);

  /* get current PMC unit */
  short pmc_get_current_pmc_unit(int FlibHndl, RefObject<Integer> a);

  /* get number of PMC */
  short pmc_get_number_of_pmc(int FlibHndl, RefObject<Integer> a);

  /* get PMC unit types */
  short pmc_get_pmc_unit_types(int FlibHndl, int[] a, RefObject<Integer> b);

  /* set PMC Timer type */
  short pmc_set_timer_type(int FlibHndl, short a, short b, RefObject<Short> c);

  /* get PMC Timer type */
  short pmc_get_timer_type(int FlibHndl, short a, short b, RefObject<Short> c);

  /*----------------------------*/
  /* PMC : PROFIBUS function    */
  /*----------------------------*/
  /* read PROFIBUS configration data */
  short pmc_prfrdconfig(int FlibHndl, ODBPRFCNF a);

  /* read bus parameter for master function */
  short pmc_prfrdbusprm(int FlibHndl, IODBBUSPRM a);

  /* write bus parameter for master function */
  short pmc_prfwrbusprm(int FlibHndl, IODBBUSPRM a);

  /* read slave parameter for master function */
  short pmc_prfrdslvprm(int FlibHndl, short a, IODBSLVPRM b);

  short pmc_prfrdslvprm(int FlibHndl, short a, IODBSLVPRM2 b);

  /* write slave parameter for master function */
  short pmc_prfwrslvprm(int FlibHndl, short a, IODBSLVPRM b);

  short pmc_prfwrslvprm(int FlibHndl, short a, IODBSLVPRM2 b);

  /* read allocation address for master function */
  short pmc_prfrdallcadr(int FlibHndl, short a, IODBPRFADR b);

  /* set allocation address for master function */
  short pmc_prfwrallcadr(int FlibHndl, short a, IODBPRFADR b);

  /* read allocation address for slave function */
  short pmc_prfrdslvaddr(int FlibHndl, IODBSLVADR a);

  /* set allocation address for slave function */
  short pmc_prfwrslvaddr(int FlibHndl, IODBSLVADR a);

  /* read status for slave function */
  short pmc_prfrdslvstat(int FlibHndl, ODBSLVST a);

  /* Reads slave index data of master function */
  short pmc_prfrdslvid(int FlibHndl, short a, IODBSLVID b);

  /* Writes slave index data of master function */
  short pmc_prfwrslvid(int FlibHndl, short a, IODBSLVID b);

  /* Reads slave parameter of master function(2) */
  short pmc_prfrdslvprm2(int FlibHndl, short a, IODBSLVPRM3 b);

  /* Writes slave parameter of master function(2) */
  short pmc_prfwrslvprm2(int FlibHndl, short a, IODBSLVPRM3 b);

  /* Reads DI/DO parameter of master function */
  short pmc_prfrddido(int FlibHndl, short a, IODBDIDO b);

  /* Writes DI/DO parameter of master function */
  short pmc_prfwrdido(int FlibHndl, short a, IODBDIDO b);

  /* Reads indication address of master function */
  short pmc_prfrdindiadr(int FlibHndl, IODBINDEADR a);

  /* Writes indication address of master function */
  short pmc_prfwrindiadr(int FlibHndl, IODBINDEADR a);

  /* Reads operation mode of master function */
  short pmc_prfrdopmode(int FlibHndl, RefObject<Short> a);

  /* Writes operation mode of master function */
  short pmc_prfwropmode(int FlibHndl, short a, RefObject<Short> b);

  /*-----------------------------------------------*/
  /* DS : Data server & Ethernet board function    */
  /*-----------------------------------------------*/
  /* read the parameter of the Ethernet board */
  short etb_rdparam(int FlibHndl, short a, IODBETP_TCP b);

  short etb_rdparam(int FlibHndl, short a, IODBETP_HOST b);

  short etb_rdparam(int FlibHndl, short a, IODBETP_FTP b);

  short etb_rdparam(int FlibHndl, short a, IODBETP_ETB b);

  /* write the parameter of the Ethernet board */
  short etb_wrparam(int FlibHndl, IODBETP_TCP a);

  short etb_wrparam(int FlibHndl, IODBETP_HOST a);

  short etb_wrparam(int FlibHndl, IODBETP_FTP a);

  /* read the error message of the Ethernet board */
  short etb_rderrmsg(int FlibHndl, short a, ODBETMSG b);

  /* read the mode of the Data Server */
  short ds_rdmode(int FlibHndl, RefObject<Short> a);

  /* write the mode of the Data Server */
  short ds_wrmode(int FlibHndl, short a);

  /* read information of the Data Server's HDD */
  short ds_rdhddinfo(int FlibHndl, ODBHDDINF a);

  /* read the file list of the Data Server's HDD */
  short ds_rdhdddir(int FlibHndl, Object a, int b, OutObject<Short> c, ODBHDDDIR d);

  /* delete the file of the Data Serve's HDD */
  short ds_delhddfile(int FlibHndl, Object a);

  /* copy the file of the Data Server's HDD */
  short ds_copyhddfile(int FlibHndl, Object a, Object b);

  /* change the file name of the Data Server's HDD */
  short ds_renhddfile(int FlibHndl, Object a, Object b);

  /* execute the PUT command of the FTP */
  short ds_puthddfile(int FlibHndl, Object a, Object b);

  /* execute the MPUT command of the FTP */
  short ds_mputhddfile(short hLib, Object a);

  /* read information of the host */
  short ds_rdhostinfo(int FlibHndl, OutObject<Integer> a, int b);

  /* read the file list of the host */
  short ds_rdhostdir(int FlibHndl, short a, int b, OutObject<Short> c, ODBHOSTDIR d, int e);

  /* read the file list of the host 2 */
  short ds_rdhostdir2(int FlibHndl, short a, int b, OutObject<Short> c, OutObject<Integer> d, ODBHOSTDIR e, int f);

  /* delete the file of the host */
  short ds_delhostfile(int FlibHndl, Object a, int b);

  /* execute the GET command of the FTP */
  short ds_gethostfile(int FlibHndl, Object a, Object b);

  /* execute the MGET command of the FTP */
  short ds_mgethostfile(int FlibHndl, Object a);

  /* read the execution result */
  short ds_rdresult(int FlibHndl);

  /* stop the execution of the command */
  short ds_cancel(int FlibHndl);

  /* read the file from the Data Server */
  short ds_rdncfile(int FlibHndl, short a, Object b);

  /* read the file from the Data Server 2 */
  short ds_rdncfile2(int FlibHndl, Object a);

  /* write the file to the Data Server */
  short ds_wrncfile(int FlibHndl, short a, int b);

  /* read the file name for the DNC operation in the Data Server's HDD */
  short ds_rddnchddfile(int FlibHndl, Object a);

  /* write the file name for the DNC operation in the Data Server's HDD */
  short ds_wrdnchddfile(int FlibHndl, Object a);

  /* read the file name for the DNC operation in the host */
  short ds_rddnchostfile(int FlibHndl, OutObject<Short> a, Object b);

  /* write the file name for the DNC operation in the host */
  short ds_wrdnchostfile(int FlibHndl, Object a);

  /* read the connecting host number */
  short ds_rdhostno(int FlibHndl, OutObject<Short> a);

  /* read maintenance information */
  short ds_rdmntinfo(int FlibHndl, short a, DSMNTINFO b);

  /* check the Data Server's HDD */
  short ds_checkhdd(int FlibHndl);

  /* format the Data Server's HDD */
  short ds_formathdd(int FlibHndl);

  /* create the directory in the Data Server's HDD */
  short ds_makehdddir(int FlibHndl, Object a);

  /* delete directory in the Data Server's HDD */
  short ds_delhdddir(int FlibHndl, Object a);

  /* change the current directory */
  short ds_chghdddir(int FlibHndl, Object a);

  /* execute the PUT command according to the list file */
  short ds_lputhddfile(int FlibHndl, Object a);

  /* delete files according to the list file */
  short ds_ldelhddfile(int FlibHndl, Object a);

  /* execute the GET command according to the list file */
  short ds_lgethostfile(int FlibHndl, Object a);

  /* read the directory for M198 operation */
  short ds_rdm198hdddir(int FlibHndl, Object a);

  /* write the directory for M198 operation */
  short ds_wrm198hdddir(int FlibHndl);

  /* read the connecting host number for the M198 operation */
  short ds_rdm198host(int FlibHndl, OutObject<Short> a);

  /* write the connecting host number for the M198 operation */
  short ds_wrm198host(int FlibHndl);

  /* write the connecting host number */
  short ds_wrhostno(int FlibHndl, short a);

  /* search string in data server program */
  short ds_searchword(int FlibHndl, Object a);

  /* read the searching result */
  short ds_searchresult(int FlibHndl);

  /* read file in the Data Server's HDD */
  short ds_rdfile(int FlibHndl, Object a, Object b);

  /* write file in the Data Server's HDD */
  short ds_wrfile(int FlibHndl, Object a, Object b);

  /*--------------------------*/
  /* HSSB multiple connection */
  /*--------------------------*/
  /* read number of node */
  short cnc_rdnodenum(OutObject<Integer> a);

  /* read node informations */
  short cnc_rdnodeinfo(int a, ODBNODE b);

  /* set default node number */
  short cnc_setdefnode(int a);

  /* allocate library handle 2 */
  short cnc_allclibhndl2(int node, OutObject<Short> FlibHndl);

  /*---------------------*/
  /* Ethernet connection */
  /*---------------------*/
  /* allocate library handle 3 */
//  short cnc_allclibhndl3(String ip, short port, long timeout, int FlibHndl);
  short cnc_allclibhndl3(String ip, short port, long timeout, int[] FlibHndl);

  /* allocate library handle 4 */
  short cnc_allclibhndl4(Object ip, short port, int timeout, int id, OutObject<Short> FlibHndl);

  /* set timeout for socket */
  short cnc_settimeout(int FlibHndl, int a);

  /* reset all socket connection */
  short cnc_resetconnect(int FlibHndl);

  /* get option state for FOCAS1/Ethernet */
  short cnc_getfocas1opt(int FlibHndl, short a, OutObject<Integer> b);

  /* read Ethernet board information */
  short cnc_rdetherinfo(int FlibHndl, OutObject<Short> a, OutObject<Short> b);
}
