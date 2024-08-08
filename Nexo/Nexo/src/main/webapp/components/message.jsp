<%
String message = (String) session.getAttribute("message");

if (message != null) {
%>
<div class="alert alert-warning alert-dismissible fade show d-flex align-items-center" role="alert" style="font-size: 12px;display: flex">
    <strong><%=message %></strong>
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>

<%
    session.removeAttribute("message");
}
%>
