<?php
define("RDFAPI_INCLUDE_DIR", "c:/wamp/www/WebAvancee/lib/rdf_api/api/"); 
include(RDFAPI_INCLUDE_DIR . "RdfAPI.php");

include_once 'classes/GoogleBookApiCaller.php';
include_once 'classes/Author.php';
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
echo ' <h1> testCurl</h1> ';

$googleapi = new GoogleBookApiCaller();

$googleapi->callAuthor("yasmina khadra",'fr',true);

$auteur1 = new Author("Blazo");

$book1 = new Book("R1g7aLtLMAkC","L'olympe des infortunes","Robert Laffont/bouquins/segher","2010-09-30",
        "153","2260018335","9782260018339");

$auteur1->Add_Book($book1);

echo '</br>';
$auteur1->Generate_RDF();

?>
