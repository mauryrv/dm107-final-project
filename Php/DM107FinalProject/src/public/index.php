<?php 

    use \Psr\Http\Message\ServerRequestInterface as Request;
    use \Psr\Http\Message\ResponseInterface as Response;
    
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
$app->get('/api/getNome/{nome}', function (Request $request, Response $response) {
        $nome = $request->getAttribute('nome');
        $response->getBody()->write("Bem vindo a API, $nome");
        
        return $response;
    });

   $app->put('/api/delivery', function (Request $request, Response $response) {
        $body = $request->getBody();

         if(empty($body["id"])){
            return $response->withJson("Id obrgatório!");
        }
        $id = $body["id"];

        if(empty($body["nome"])){
            return $response->withJson("Nome obrgatório!");
        }
        $nome = $body["nome"];

         if(empty($body["cpf"])){
            return $response->withJson("Cpf obrgatório!");
        }
         $cpf = $body["cpf"];

        if(empty($body["data"])){
            return $response->withJson("Data e hora obrgatório no formato dd/mm/yyyy hh:mm ss!");
        }
         $data = $body["data"];

       //checa se existe a entrega
        $entrega = $this->db->delivery("id=?",$idEntrega)->fetch();

        if(empty($entrega)){
            return $response->withStatus(404);
        }
        //update a entrega com os itens necessários
        $entregaUpdate =array(
        "receiver_name" => $nome,
        "receiver_cpf" => $cpf,
        "delivery_date" => $data
        );
        $result = $entrega->update($entregaUpdate);
        
        //pega a entrega depois de atualizada e mostra a mesma
        $entrega = $this->db->delivery("id=?",$id)->fetch();
        
        return $response->withJson($entrega);
    });

    $app->delete('/api/delivery/{idEntrega}', function (Request $request, Response $response) {
       
        $idEntrega = $request->getAttribute("idEntrega");
        //checa se existe a entrega
        $entrega = $this->db->delivery("id=?",$idEntrega)->fetch();
        if(empty($entrega)){
            return $response->withStatus(404);
        }
        //delete a entrega
        $this->db->delivery("id=?",$idEntrega)->delete(); //verificar modo correto de fazer


       return $response->withJson("{Delete:ok}", 200);
        //return $response->withJson($tarefas);
    });


    $app->run();
    
?>