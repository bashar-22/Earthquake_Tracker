<%@ page import="it.unipi.dstm.EarthquakeDTO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Bashar
  Date: 4/19/2022
  Time: 12:32 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hi ...</title>
    <script type = text/javascript>
        var ws;
        var host = document.location.host;
        var pathname = document.location.pathname;
        // for local
       // const url = "ws://localhost:8080/webapp-test/MessageSocket1";
// for remote
        const url = "wss://" + host  + "/webapp-test/MessageSocket1";
        ws = new WebSocket(url);

        ws.onmessage = function(event) {
            console.log(event.data);
        let textarea = document.getElementById("AlertBox");
        textarea.value+= event.data +"\n";
        };



    </script>
</head>
<%
    List<EarthquakeDTO> list = (List<EarthquakeDTO>) request.getAttribute("earthquakesList");

%>
<body>
<h1 id="titleRegion"></h1>

<label for="AlertBox"></label><textarea id="AlertBox" rows="4" cols="50"></textarea>

<br>
<form action="<%= request.getContextPath()%>/earthInfo">
    <p>Please select your region to retrieve the last updates on earthquakes data: </p>

    <% if (request.getAttribute("regionToDisplay").equals("allRegions")) {%>
        <label>
            <input type="radio" name="region" value="current">
        </label>Current Region

        <label>
            <input type="radio" name="region" value="all" checked>
        </label>All Regions
    <% } else {%>
        <label>
            <input type="radio" name="region" value="current" checked>
        </label>Current Region

        <label>
            <input type="radio" name="region" value="all" >
        </label>All Regions
    <% } %>

    <br>
    <label for="start">From date:</label>

    <input type="date" id="start" name="startDate"
           min="2022-01-01" max="2050-12-31"  value='<%= request.getAttribute("startDateValue") != null ? request.getAttribute("startDateValue") : "" %>'>


    <label for="end">To date:</label>

    <input type="date" id="end" name="endDate"
           min="2022-01-01" max="2050-12-31"  value='<%= request.getAttribute("endDateValue") != null ? request.getAttribute("endDateValue") : "" %>'>



    <input type="submit" value="Filter"/>
</form>
<br>
<% if (request.getAttribute("regionToDisplay").equals("allRegions")) {%>
<table style="width:100%">
    <tr>
        <th>Magnitude</th>
        <th>Latitude</th>
        <th>Longitude</th>
        <th>Depth</th>
        <th>Date</th>
        <th>Region</th>

    </tr>
    <% if(list!=null){%>
    <% for(EarthquakeDTO dto:list) {%>
    <tr>
        <td style="text-align: center"><%=dto.getMagnitude()%></td>
        <td style="text-align: center"><%=dto.getLatitude()%></td>
        <td style="text-align: center"><%=dto.getLongitude()%></td>
        <td style="text-align: center"><%=dto.getDepth()%></td>
        <td style="text-align: center"><%=dto.getDate()%></td>
        <td style="text-align: center"><%=dto.getRegion()%></td>


    </tr>
    <% }%>
    <% }%>
</table>

<% } else {%>
<table style="width:100%">
    <tr>
        <th>Magnitude</th>
        <th>Latitude</th>
        <th>Longitude</th>
        <th>Depth</th>
        <th>Date</th>
<%--        <th>Region</th>--%>

    </tr>
    <% if(list!=null){%>
    <% for(EarthquakeDTO dto:list) {%>
    <tr>
        <td style="text-align: center"><%=dto.getMagnitude()%></td>
        <td style="text-align: center"><%=dto.getLatitude()%></td>
        <td style="text-align: center"><%=dto.getLongitude()%></td>
        <td style="text-align: center"><%=dto.getDepth()%></td>
        <td style="text-align: center"><%=dto.getDate()%></td>
<%--        <td><%=dto.getRegion()%></td>--%>


    </tr>
    <% }%>
    <% }%>
</table>
<% } %>



</body>
<script type="text/javascript">
    let  txt="Earthquakes Tracker System for ";
    if(host==="172.18.0.66:8181" || host==="localhost:8080"){
        txt+="Region#1"

    }else if(host==="172.18.0.65:8181")
    {
        txt+="Region#2"
    }
    else if(host==="172.18.0.67:8181")
    {
        txt+="Region#3"
    }
    document.getElementById("titleRegion").innerText=txt
</script>
</html>
