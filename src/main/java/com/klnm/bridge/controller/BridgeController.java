package com.klnm.bridge.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.klnm.bridge.service.HocUserService;
import com.klnm.bridge.service.UserService;
import com.oreilly.servlet.MultipartRequest;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;


@MapperScan(basePackages = {"com.klnm.bridge.hoc"})
@Controller
public class BridgeController {

    @Autowired
    UserService userService;
    @Autowired
    HocUserService hocUserService;

    String savePath = "C:\\test";

    @RequestMapping("/query")
    public @ResponseBody List<HashMap> query() throws Exception {
        //return userService.getAll();
        return new AbstractList<HashMap>() {
            @Override
            public HashMap get(int index) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }
        };
    }


    @RequestMapping("/imageUpload.do")
    public void imageUpload(HttpServletRequest request) throws Exception {

        int sizeLimit = 100 *1024 * 1024;

        System.out.println("imageUpload======"+request);

        try{

            //request.get


            MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "UTF-8", new DefaultFileRenamePolicy());

            String phoneNum = multi.getParameter("phoneNum");
            File pic = multi.getFile("pic");


            System.out.println("phoneNum======"+phoneNum);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @RequestMapping(value="/add/imageUpload", method= RequestMethod.POST)
    public ResponseEntity imageUpload(@RequestParam("phoneNum") Long phoneNum, HttpServletResponse response, HttpServletRequest request)
    {
        try {
            MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest)request;
            Iterator<String> it=multipartRequest.getFileNames();
            MultipartFile multipart=multipartRequest.getFile(it.next());
            String fileName=phoneNum+".png";
            String imageName = fileName;

            byte[] bytes=multipart.getBytes();
            BufferedOutputStream stream= new BufferedOutputStream(new FileOutputStream(savePath + "/" + fileName));

            stream.write(bytes);
            stream.close();
            return new ResponseEntity("upload success", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Upload fialed", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/add/joinMember2", method= RequestMethod.POST)
    public ResponseEntity joinMember(HttpServletRequest request
                                     , HttpServletResponse response
                                     , @RequestParam(value = "jsonParam", required = false) String param)
    {
        try {


            String jsonParam = (String) request.getParameter("jsonParam");

            System.out.println("jsonParam======"+ jsonParam);
            System.out.println("param======"+ param.toString());

            return new ResponseEntity("joinMember success", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("joinMember fialed", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/add/joinMember", method= RequestMethod.POST)
    public ResponseEntity postServer(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("POST Server Response");
        System.out.println("==================request body===========================");

        String[] paramList = null;
        InputStream in = request.getInputStream();
        String returnStr = "";

        if (in != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String line = URLDecoder.decode(br.readLine());
            System.out.println(line);

            paramList = line.split("&");

        }

        for(int i =0; i < paramList.length; i++){
            try {

                String title = paramList[i].substring(0, paramList[i].indexOf("="));
                String param = paramList[i].substring(paramList[i].indexOf("=") + 1);

                System.out.println("paramList===" + title + " i : " + i + "===" + param);

                if("joinMember".equals(title)){

                    int cnt = hocUserService.insertUser(param);
                    if(cnt > 0 ){
                        returnStr = "upload success";
                    }
                } else if ("idCheck".equals(title)){
                    int cnt = hocUserService.checkId(param);
                    System.out.println("paramList===" + title + " i : " + i + "===" + param +"==cnt=="+ cnt);
                    if(cnt == 0 ){
                        returnStr = "success";
                    } else {
                        returnStr = "fail";
                    }
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return new ResponseEntity(returnStr, HttpStatus.OK);

        //클라이언트 페이지로 부터 Httpclient로 받은 parameter값

    }




}
