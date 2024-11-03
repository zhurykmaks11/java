<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reports</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h1>Reports</h1>

<!-- Форма для створення нового звіту -->
<form action="/reports/create" method="post">
    <label for="norderId">Order ID:</label>
    <input type="text" id="norderId" name="norderId" required>

    <label for="date">Date:</label>
    <input type="date" id="date" name="date" required>

    <label for="status">Status:</label>
    <input type="text" id="status" name="status" required>

    <button type="submit">Create Report</button>
</form>

<!-- Таблиця для відображення звітів -->
<table>
    <thead>
    <tr>
        <th>Report ID</th>
        <th>Order ID</th>
        <th>Date</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${reports}" var="report">
        <tr>
            <td>${report.id}</td>
            <td>${report.norderId}</td>
            <td>${report.date}</td>
            <td>${report.status}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p>lagdgskgskgsbg;sgpisbg</p>
</body>
</html>
