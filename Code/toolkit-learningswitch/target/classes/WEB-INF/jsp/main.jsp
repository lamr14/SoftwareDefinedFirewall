<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Firewall</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- style -->
        <link rel="stylesheet" href="/css/ext/pure/pure.css"/>
        <link rel="stylesheet" href="/css/phoenix.css"/>

        <!-- style app -->
        <link rel="stylesheet" href="/learningswitch/web/css/simple.css"/>

        <!-- scripts -->
        <script src="/js/ext/jqueryNew.js"></script>
        <script src="/learningswitch/web/js/myScript.js"></script>

    </head>

    <body>


        <h1> Firewall </h1>
        
        <form class="pure-form" onsubmit="return false;">
        
        	<fieldset class="pure-group">
		        <div class="pure-u-1 pure-u-md-1-3">
	                <select id="selection" class="pure-input-1-4">
	                    <option>Protocols</option>
	                    <option>Ports</option>
	                    <option>IP Addresses</option>
	                    <option>MAC Addresses</option>
	                </select>
	            </div>
	        </fieldset>
        
        
        	<fieldset class="pure-group">
        		<input type="text" id="input" class="pure-input-1-4" placeholder="Protocol / Port / IP / MAC"/>
        	</fieldset>
        	
        	<fieldset class="pure-group">
        		<button id="block" class="pure-button pure-input-1-4 button-error">Block</button>
        	</fieldset>
        	
        	<fieldset class="pure-group">
	        	<button id="unblock" class="pure-button pure-input-1-4 button-success">Unblock</button>
	        </fieldset>
	        
	        <fieldset class="pure-group">
	        	<button id="refreshList" class="pure-button pure-input-1-4 pure-button-primary">Refresh Blocked List</button>
	        </fieldset>
	        
        </form>

        <!-- full-width outer box  -->

        <h3> Blocked List </h3>
        
    	<table class="pure-table">
		    <thead>
		        <tr>
		            <th>#</th>
		            <th>Blocked</th>
		        </tr>
		    </thead>
		    
		    <tbody id="blockedList">
		    </tbody>
		    
		</table>

        <p /><p />

    </body>
</html>
