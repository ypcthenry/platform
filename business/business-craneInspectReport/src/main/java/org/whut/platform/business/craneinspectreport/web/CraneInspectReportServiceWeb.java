package org.whut.platform.business.craneinspectreport.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
<<<<<<< HEAD
=======
import org.whut.platform.business.address.entity.Address;
import org.whut.platform.business.address.service.AddressService;
>>>>>>> 23dd51744e660700d6196a2d52cb2394d49b9f1c
import org.whut.platform.business.craneinspectreport.entity.CraneInspectReport;
import org.whut.platform.business.craneinspectreport.service.CraneInspectReportService;
import org.whut.platform.fundamental.config.FundamentalConfigProvider;
import org.whut.platform.fundamental.fileupload.FileInfo;
import org.whut.platform.fundamental.fileupload.FileService;
import org.whut.platform.fundamental.fileupload.MultipartRequestParser;
import org.whut.platform.fundamental.logger.PlatformLogger;
import org.whut.platform.fundamental.map.BaiduMapUtil;
import org.whut.platform.fundamental.util.json.JsonResultUtils;
import javax.servlet.http.HttpServletRequest;
<<<<<<< HEAD
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
=======
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
>>>>>>> 23dd51744e660700d6196a2d52cb2394d49b9f1c

/**
 * Created with IntelliJ IDEA.
 * User: zhuzhenhua
 * Date: 14-3-17
 * Time: 下午10:40
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("/craneinspectreport")
public class CraneInspectReportServiceWeb {
    private static PlatformLogger logger = PlatformLogger.getLogger(CraneInspectReportServiceWeb.class);
    @Autowired
    private CraneInspectReportService craneInspectReportService;
    @Autowired
    private AddressService addressService;
    private BaiduMapUtil baiduMapUtil=new BaiduMapUtil();
    private MultipartRequestParser multipartRequestParser=new MultipartRequestParser();
    @Produces(MediaType.MULTIPART_FORM_DATA)
    @Path("/upload")
    @POST
    public String upload(@Context HttpServletRequest request){
        if (request == null) {
            return JsonResultUtils
                    .getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
        try {
            request.setCharacterEncoding("UTF-8");
            int uploadMaxSize= Integer.parseInt(FundamentalConfigProvider.get("uploadMaxSize"));
            FileService fileService=new FileService("xls");
            FileInfo fileInfo=new FileInfo();
            fileInfo=parseRequst(request,multipartRequestParser,fileService,uploadMaxSize);
            if(fileInfo.getName()==null){
                //文件名为空
            }else{
                craneInspectReportService.upload(fileInfo.getInputStream(),fileInfo.getName());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(e.getMessage());
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
        // 新增操作时，返回操作状态和状态码给客户端，数据区是为空的
        List<CraneInspectReport> list=craneInspectReportService.getRepeatList();
        if (list.size()==0||list==null){
            return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
        }
        else{
            return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.DUPLICATE);
        }
    }
      public FileInfo parseRequst(@Context HttpServletRequest request,MultipartRequestParser multipartRequestParser,FileService fileService,int uploadMaxSize){
               FileInfo fileInfo=null;
               try{
               fileInfo=multipartRequestParser.parseRequest(request,uploadMaxSize,fileService);
               }catch (Exception e){
                   e.printStackTrace();
               }
             return fileInfo;
      }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
<<<<<<< HEAD
    @Path("/list")
    @GET
      public String list(){
          List<CraneInspectReport> list=craneInspectReportService.list();
          return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
      }

//    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
//    @Path("/listRepeat")
//    @GET
//    public String listRepeat(){
//        List<CraneInspectReport> listRepeat=craneInspectReportService.getRepeatList();
//        return JsonResultUtils.getObjectResultByStringAsDefault(listRepeat,JsonResultUtils.Code.DUPLICATE);
//    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/addRepeat")
    @POST
    public String addRepeat(){
        CraneInspectReport craneInspectReport=new CraneInspectReport();
        List<CraneInspectReport> list=craneInspectReportService.getRepeatList();
        for(CraneInspectReport repeat:list){
            craneInspectReport.setReportNumber(repeat.getReportNumber());
            craneInspectReport.setUnitAddress(repeat.getUnitAddress());
            craneInspectReport.setAddressId(repeat.getAddressId());
            craneInspectReport.setOrganizeCode(repeat.getOrganizeCode());
            craneInspectReport.setUserPoint(repeat.getUserPoint());
            craneInspectReport.setSafeManager(repeat.getSafeManager());
            craneInspectReport.setContactPhone(repeat.getContactPhone());
            craneInspectReport.setEquipmentVariety(repeat.getEquipmentVariety());
            craneInspectReport.setUnitNumber(repeat.getUnitNumber());
            craneInspectReport.setManufactureUnit(repeat.getManufactureUnit());
            craneInspectReport.setManufactureLicenseNumber(repeat.getManufactureLicenseNumber());
            craneInspectReport.setManufactureDate(repeat.getManufactureDate());
            craneInspectReport.setSpecification(repeat.getSpecification());
            craneInspectReport.setpNumber(repeat.getpNumber());
            craneInspectReport.setWorkLevel(repeat.getWorkLevel());
            craneInspectReportService.update(craneInspectReport);
        }
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
=======
    @Path("/getAreaInfo")
    @POST
    public String getAreaInfo(@FormParam("city") String city,@FormParam("pname") String pname){
        if(city==null||city.trim().equals("")||pname==null||pname.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
        Long addressId=addressService.findIdByCityArea(city,pname);
        if(addressId==null){
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }else{
        List<CraneInspectReport> list=craneInspectReportService.getInfoByAddressId(addressId);
        if(list==null){
          return  JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }else{
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
        }
        }
    }
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/getAreaInfoByUnitAddress")
    public String  getAreaInfoByUnitAddress(@FormParam("name") String name){
        if(name==null||name.trim().equals("")){
            return  JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
        List<CraneInspectReport> list=craneInspectReportService.getInfoByUnitAddress(name);
        return JsonResultUtils.getObjectStrResultByStringAsDefault(list,200,name);
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @POST
    @Path("/getCraneInspectReportInfoByAddressAndEquipment")
    public String getCraneInspectReportInfoByAddressAndEquipment(@FormParam("address_equipmentvariety") String address_equipmentvariety,@FormParam("itemInfoId") String itemInfo){
        if(address_equipmentvariety==null||address_equipmentvariety.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
           String[] str=address_equipmentvariety.split(",");
           List<CraneInspectReport> list=craneInspectReportService.getCraneInspectReportInfoByAddressAndEquipment(str[0],str[1]);
           return JsonResultUtils.getObjectStrResultByStringAsDefault(list,200,itemInfo);
    }

    @Produces(MediaType.MULTIPART_FORM_DATA)
    @Path("/getUnitaddressByArea")
    @POST
    public String getUnitaddressByArea(@FormParam("province") String province,@FormParam("city") String city,@FormParam("area") String area)
    {
        List<CraneInspectReport> list=craneInspectReportService.getUnitaddressByArea(province,city,area);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);

>>>>>>> 23dd51744e660700d6196a2d52cb2394d49b9f1c
    }
}
