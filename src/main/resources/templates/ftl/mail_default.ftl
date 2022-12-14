<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>${title}</title>
    <style>
        * {
            border: 0;
            margin: 0;
        }

        html, body, .bg {
            width: 100%;
            height: 100%;
            position: relative;
            background: #f2f2f2 !important;
        }

        .top {
            height: 80px;
            width: 100%;
            position: relative;
            background-color: white;
            border-bottom: 1px solid #e2e5e8;
        }

        .top *, .center * {
            background: white !important;
        }

        .p-word-break {
            text-indent: 28px;
            word-break: break-all;
            white-space: pre-line;
            word-wrap: break-word;
        }

        .top p {
            left: 5px;
            height: 80px;
            line-height: 80px;
            font-family: Helvetica, Arial, sans-serif;
            color: #333333;
            font-size: 24px;
            font-weight: bold;
        }

        .center {
            top: 20px;
            min-height: 150px;
            background-color: white;
            border-bottom: 2px solid #e2e5e8;
        }

        .center > div {
            display: table-cell;
            vertical-align: middle;
        }

        .center p {
            text-align: left;
            font-size: 14px;
            font-family: Helvetica, Arial, sans-serif;
            color: #555555;
        }

        .footer {
            position: relative;
            top: 40px;
        }

        .footer p {
            font-size: 13px;
            line-height: 18px;
            font-family: Helvetica, Arial, sans-serif;
            color: #aaaaaa;
        }

        .center-align {
            width: 80%;
            max-width: 600px;
            position: relative;
            margin: 0 auto;
            display: table;
        }

        @media screen and (max-width: 525px) {
            .center-align {
                left: 0 !important;
                width: 100%;
            }
        }

    </style>
</head>
<body>
<div class="bg">
    <div class="top">
        <p class="title center-align">
            ${title}
        </p>
    </div>
    <div class="center center-align">
        <div class="p-word-break">
            <p>${text}</p>
        </div>
    </div>
    <div class="footer center-align p-word-breakx">
        <p>You???re receiving this email because you have signed up for our products and services.
            ${com}</p>
    </div>
</div>
</body>
</html>