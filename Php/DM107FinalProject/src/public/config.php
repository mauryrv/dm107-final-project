<?php

require '../vendor/autoload.php';

$config['displayErrorDetails'] = true; 
$config['addContentLengthHeader'] = false;

 $config['db']['host'] = "localhost"; 
 $config['db']['user'] = "root"; 
 $config['db']['pass'] = "root"; 
 $config['db']['dbname'] = "TrabalhoDM107";
 
$app = new \Slim\App(["config" => $config]);
$container = $app->getContainer();
$container['db'] = function ($c) { 
    $dbConfig = $c['config']['db']; 
    $pdo = new PDO("mysql:host=" . $dbConfig['host'] . ";dbname=" . $dbConfig['dbname'], 
        $dbConfig['user'], $dbConfig['pass']); 
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); 
    $pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC); 
    $db = new NotORM($pdo); 
    return $db; 
};

?>