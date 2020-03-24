<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<title>扫码登录</title>
    <link type="text/css" rel="stylesheet" href="/static/css/login.css">
    <script type="text/javascript" src="/static/js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="/static/js/login.js"></script>
    <script type="text/javascript">
       $(function(){
    	   sacnCode(); 
    	   
       });
       
       /* function sacnCode(){
           $.post("./dingUser/login",function(result){
        	   if(result!=null){
        		   $("#scanCode").attr("src", result);
        	   }
           });
    	
    	} */
       function sacnCode(){
    	   $.ajax({
               type: "POST",
               url: "/dingUser/login",
               success: function(data){
            	   if(data!=null){
            		   $("#scanCode").attr("src", data);
            	   }
               }
           });
    	   
    	   
       }
    </script>
</head>
<body style="overflow:hidden" class="pagewrap">
    <div >
        <div class="main">
            <div class="header"></div>
            <div class="content">
            <div class="con_left"></div>
                <div class="con_right">
                    <div class="con_r_top">
                    <a href="javascript:;" onclick="sacnCode();" class="left" style="color: rgb(51, 51, 51); border-bottom-width: 2px; border-bottom-style: solid; border-bottom-color: rgb(46, 85, 142);">扫码登录</a> 
                    <a href="/dingUser/login3" target="myFrameName" class="right" style="color: rgb(153, 153, 153); border-bottom-width: 2px; border-bottom-style: solid; border-bottom-color: rgb(222, 222, 222);">钉钉账号登录</a></div>
                    <ul>
                        <li class="con_r_left" style="display: block;">
                            <div class="erweima">
                                <div class="qrcode">
                                    <div id="output" style="width: 100%; position: relative">
                                        <iframe id="scanCode" style="padding:0px;overflow:hidden;margin-left:-15px;margin-top:-30px; width: 381px; height: 340px" scrolling="no"  frameborder="0"></iframe>                               
                                </div>
                             </div>
                        </li>
                        <li class="con_r_right" style="display: none;">
                        <iframe id="myFrameId" name="myFrameName" style="padding:0px;overflow:hidden;margin-left:50px;margin-top:-30px; width: 381px; height: 400px" frameborder="0"></iframe> 
                        </li>
                    </ul>
                </div>
           <div> <a href="/PoiExport/obtainBillpdf?orderId=156987845787541225&accountId=allright"  style="color: rgb(153, 153, 153);">点击生成pdf</a> </div>
            </div>
        </div>
    </div>
  
</body></html>