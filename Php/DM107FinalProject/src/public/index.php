<?php 
    require 'config.php';

    $app->put('/api/delivery', function (Request $request, Response $response) {
        $body = $request->getBody();
        $id = $body["id"];
       //checa se existe a entrega
        $entrega = $this->db->delivery("id=?",$idEntrega)->fetch();

        if(isset($entrega)){
            return $response->withStatus(404);
        }
        //update a entrega com os itens necessários


        //pega a entrega depois de atualizada e mostra a mesma
        $entrega = $this->db->delivery("id=?",$idEntrega)->fetch();


        return $response->withJson($entrega);
    });

    $app->delete('/api/delivery/{idEntrega}', function (Request $request, Response $response) {
       
        $idEntrega = $request->getAttribute("idEntrega");
        //checa se existe a entrega
        $entrega = $this->db->delivery("id=?",$idEntrega)->fetch();
        if(isset($entrega)){
            return $response->withStatus(404);
        }
        //delete a entrega



        $newResponse = $response->withJson("{Delete:ok}", 200);
        //return $response->withJson($tarefas);
    });


    $app->run();
?>