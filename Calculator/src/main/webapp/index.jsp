<!DOCTYPE html>
<html>
<head>
    <title>Calculator</title>
        <script>
        // kullanicinin sadece rakam girebilmesi icin fonksiyon
        function onlyNumbers(evt) {
            var charCode = (evt.which) ? evt.which : evt.keyCode;
            if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <h1>Calculator</h1>
    <form action="calculate" method="post">
		Num 1: <input type="text" name="num1" onkeypress="return onlyNumbers(event)"><br>
        Num 2: <input type="text" name="num2" onkeypress="return onlyNumbers(event)"><br>
        Operator: 
        <select name="operation">
            <option value="add">add (+)</option>
            <option value="subtract">subtract (-)</option>
            <option value="multiply">multiply (*)</option>
            <option value="divide">divide (/)</option>
        </select><br>
        <input type="submit" value="Calculate">
    </form>
</body>
</html>
