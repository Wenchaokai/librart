jQuery(function($){
    var form = $('#field-warm-form');
    
    function validFields(){
    	var field = document.getElementsByName("field");
    	for (var i = 0; i < field.length; i++){ 
    		if (field[i].checked)
    			return true;
        }
    	alert("请选择至少一个字段");
    	return false;
    }
    
    form.on('submit',function(){
        return validFields();
    });

});
