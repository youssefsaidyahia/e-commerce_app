<?php
$response=array();
$con=mysqli_connect("localhost","id7367722_youssef_data","youssef97","id7367722_online_shoopping");
$result=mysqli_query($con,"SELECT *FROM categories");
if(mysqli_num_rows($result)>0){
    $response["categories"]=array();
    while($row=mysqli_fetch_array($result)){
        $category = array();
        $category["catid"]=$row["CatID"];
        $category["catname"]=$row["CatName"];
        array_push($response["categories"],$category);
    }
    mysqli_close($con);
    $response["success"]=1;
    echo json_encode($response);
}
else{
    $response["success"]=0;
    $response["message"]="there is no categories";
    echo json_encode($response);
}