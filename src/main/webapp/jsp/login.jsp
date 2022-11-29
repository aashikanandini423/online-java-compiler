<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>Code Learner</title>
</head>
<link media="all" type="text/css" href="css/home.css" rel="stylesheet">
<jsp:include page="./includes/header.jsp" flush="true" />
<body>
<div class="container">
   <h2 id="main-title" style="text-align: center">Code Learner</h2>

   <div style="padding: 70px 0;text-align: center; margin:0 auto;" id="login">
      <table width="100%">
         <tr>
            <td>
              <form method="post" action="home">
                 <div>
                     <input placeholder="User ID" class="input-text" id="userId" type="text" value="" />
                 </div>
                 <div>
                     <input placeholder="Password" class="input-password" id="password" type="password" />
                 </div>
                 <div id="bot-nav">
                    <button type="button" class="btn btn-primary" id="signup">Sign Up</button>
                    <button type="button" class="btn btn-primary" id="signin">Sign In</button>
                 </div>
              </form>
            </td>
         </tr>
      </table>
   </div>
</div>
</body>
<jsp:include page="./includes/footer.jsp" flush="true" />
<script type="text/javascript" src="js/home.js"></script>
<script type="text/javascript" src="js/urls.js"></script>
</html>