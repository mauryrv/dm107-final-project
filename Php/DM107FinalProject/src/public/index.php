<?php 

use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require 'config.php';

//get delivery - used for test
$app->get('/api/delivery/{nome}', function (Request $request, Response $response) {
        $nome = $request->getAttribute('nome');
        $entrega = $this->db->delivery("id=?",$nome)->fetch();
        
        if(empty($entrega)){
            return $response->withJson("{Status:Delivery not found}", 404);
        }

        return $response->withJson($entrega);
    });

    //update delivery
   $app->put('/api/delivery', function (Request $request, Response $response) {
        $body = $request->getBody();

        //Json format example
       /*{
            "id":"2",
            "nome":"nome entregador",
            "cpf":"00000000000",
            "dataDelivery":"10/12/2020 23:49:34"
          }*/
        $data = json_decode($body); 
   
        $id = $data->id;
        $nome = $data->nome;
        $cpf = $data->cpf;
        $dateDelivery = $data->dataDelivery;
       
       //check if the delivery exists
        $entrega = $this->db->delivery("id=?",$id)->fetch();

        if(empty($entrega)){
            return $response->withStatus(404);
        }
        //update the delivery
        $entregaUpdate =array(
        "receiver_name" => $nome,
        "receiver_cpf" => $cpf,
        "delivery_date" => date("Y-m-d H:i:s",strtotime(str_replace('/','-',$dateDelivery)))
        );
        
        $resultUpdate = $entrega->update($entregaUpdate);

        if($resultUpdate == 0){
            return $response->withStatus(500);
        }

        //get and show the delivery updated
        $entrega = $this->db->delivery("id=?",$id)->fetch();
        
        return $response->withJson($entrega);
    });

    //delete delivery
    $app->delete('/api/delivery/{idEntrega}', function (Request $request, Response $response) {
       
        $idEntrega = $request->getAttribute("idEntrega");
        //check if the delivery exists
        $entrega = $this->db->delivery("id=?",$idEntrega)->fetch();
        if(empty($entrega)){
            return $response->withStatus(404);
        }
        //delete delivery
        $this->db->delivery("id=?",$idEntrega)->delete(); //verificar modo correto de fazer


       return $response->withJson("{Delete:ok}", 200);

    });


    $app->run();
    
?>