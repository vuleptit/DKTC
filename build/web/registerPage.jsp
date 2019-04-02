<%-- 
    Document   : registerPage
    Created on : Mar 22, 2019, 10:54:46 AM
    Author     : Win7
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <header>
        <title>Đăng ký tín chỉ</title>
        <link type="text/css" rel="stylesheet" href="css/registerPage.css">
    </header>

    <body>

        <form id="search" action="tim-lop" method="post">
            <div id="searchInput">
                Mã môn học (*): 
                <input type="number" id="subId" name="subjectId" placeholder="Mã môn học" required 
                       oninvalid="this.setCustomValidity('Mời nhập mã môn học')" oninput="setCustomValidity('')">
                <input type="submit" name="searchSubmit" value="Tìm kiếm">
            </div>
        </form>

        <%if (session.getAttribute("conflict") != null) {
                if (session.getAttribute("conflict").equals("true")) {
        %>
        <div id="caution">Trùng thời khoá biểu</div>
        <%}
            }%>

        <div id="classList">
            &emsp;&emsp;&emsp;&emsp;&emsp;ID&emsp;&emsp;Chỗ trống&emsp;&emsp;&nbsp;&nbsp;&nbsp;Giảng viên&emsp;&emsp;Phòng&emsp;&emsp;Thời gian&emsp;&emsp;Số tín chỉ
            <hr width="100%">
            <form action="chon-lop" method="get">
                <%
                    List<model.ClassTKB> classes = (List<model.ClassTKB>) session.getAttribute("classes");

                    if (classes != null) {
                        for (model.ClassTKB classTKB : classes) {
                            if (model.ClassTKBDAO.isClassOnTemp(classTKB.getId())) {
                %>        
                <input type="radio" name="chosenClass" checked="checked" value="<%=classTKB.getId()%>"><%=classTKB.toString()%><br>
                <hr width="100%"/>
                <%
                } else {
                %>        
                <input type="radio" name="chosenClass" value="<%=classTKB.getId()%>"><%=classTKB.toString()%><br>
                <hr width="100%"/>
                <%
                            }
                        }
                    }
                %>
                <br>
                <div id="confirm">
                    <input id="confirmButton" type="submit" name="tempSubmit" value="Chọn">
                </div>
            </form>
        </div>


        <div id="chosenClass">
            &emsp;&emsp;&emsp;&emsp;&emsp;ID&emsp;&emsp;Chỗ trống&emsp;&emsp;&nbsp;&nbsp;&nbsp;Giảng viên&emsp;&emsp;Phòng&emsp;&emsp;Thời gian&emsp;&emsp;Số tín chỉ

            <form action="xoa-lop" method="post">
                <%
                    List<model.ClassTKB> tempClasses = model.TempDAO.listClassOnTemp();

                    if (tempClasses != null) {
                        for (model.ClassTKB classTKB : tempClasses) {
                %>
                <input type="checkbox" name="tempClasses" value="<%=classTKB.getId()%>"> <%=classTKB.toString()%> <br>
                <hr width="100%">

                <%
                        }
                    }
                %>
                <br>
                <input id="deleteButton" type="submit" name="tempDelete" value="Xoá">
            </form>

        </div>

        <%if (session.getAttribute("savingStatus") != null) {
                if (session.getAttribute("savingStatus").equals("success")) {
        %>
        <div id="status">Lưu thông tin đăng ký thành công</div>
        <%} else if (session.getAttribute("savingStatus").equals("no")) {%>
        <div id="status">Có môn hết chỗ trống, mời đăng ký lại</div>
        <%} else if (session.getAttribute("savingStatus").equals("less")) {%>
        <div id="status">Đăng ký không thành công, tổng số tín chỉ nhỏ hơn 14</div>
        <%} else if (session.getAttribute("savingStatus").equals("more")) {%>
        <div id="status">Đăng ký không thành công, tổng số tín chỉ lớn hơn 30</div>
        <%}
            }%>


        <form action="luu-dang-ky" method="post">
            <button id="buttonSave" type="submit">Lưu đăng ký</button>
        </form>

    </body>
</html>