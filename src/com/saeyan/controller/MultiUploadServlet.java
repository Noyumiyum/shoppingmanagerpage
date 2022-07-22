package com.saeyan.controller;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/upload2.do")
public class MultiUploadServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
    public MultiUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // TODO Auto-generated method stub
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // TODO Auto-generated method stub
      
      request.setCharacterEncoding("UTF-8");
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      //업로드한 파일 이름 출력위한 hashmap추가 제네릭..
      HashMap<String, String> map = new HashMap<String, String>();
      
      String savePath = "upload";
      int uploadFileSizeLimit = 5 * 1024 * 1024;
      String encType = "UTF-8";
      ServletContext context = getServletContext();
      String uploadFilePath = context.getRealPath(savePath);
      try {
         MultipartRequest multi = new MultipartRequest(request,
               uploadFilePath, uploadFileSizeLimit, encType,
               new DefaultFileRenamePolicy());
         Enumeration files = multi.getFileNames();
         
         
         while (files.hasMoreElements()) {
            String file = (String) files.nextElement();
            String file_name = multi.getFilesystemName(file);
            // 중복된 파일을 업로드할 경우 파일명이 바뀐다.
            String ori_file_name = multi.getOriginalFileName(file);
           //hashMap 2
            map.put(file_name, ori_file_name);
//            out.print("<br> 업로드된 파일명 : " + file_name);
//            out.print("<br> 원본 파일명 : " + ori_file_name);
//            out.print("<hr>");
            
         }
       //hashMap 3
         request.setAttribute("map", map);
      } catch (Exception e) {
         System.out.print("예외 발생 : " + e);
      }// catch
      
      RequestDispatcher dispatcher = request.getRequestDispatcher("fileList.jsp");
      dispatcher.forward(request, response);
   }

}
