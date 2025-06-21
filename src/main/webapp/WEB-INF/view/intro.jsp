<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <title>Old Soul Store - Intro</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@500;700&display=swap" rel="stylesheet">
    <style>
        body {
            background-color: #e0cdb4;
            font-family: 'Noto Serif TC', serif;
            color: #4b3621;
        	font-size: 1.15rem;
            margin: 0;
        }

        .intro-wrapper {
            min-height: 89vh;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            text-align: center;
            padding: 2rem;
        }

        h1 {
            font-size: 3.5rem;
            font-weight: 700;
            margin-bottom: 1rem;
            color: #5a4033;
        }

        .lead-group {
            display: flex;
            align-items: center;
            justify-content: center;
            flex-wrap: wrap;
            gap: 1rem;
        }

        .lead-text {
            font-size: 1.5rem;
            color: #6b4c3b;
        }

        .btn-home {
            font-size: 1.2rem;
            padding: 0.75rem 2rem;
            border-radius: 50px;
            background-color: #e2b77e;
            color: #2d1b00;
            border: none;
            text-decoration: none;
            transition: all 0.3s ease-in-out;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        }

        .btn-home:hover {
            background-color: #d19b60;
            transform: scale(1.05);
        }
    </style>
</head>
<body>

<div class="intro-wrapper">
    <h1>老式美好舊貨店</h1>
    <div class="lead-group">
        <p class="lead-text">舊時光的美好，等待有故事的靈魂來</p>
        <a class="btn-home" href="${pageContext.request.contextPath}/home">發掘。</a>
    </div>
</div>

</body>
</html>