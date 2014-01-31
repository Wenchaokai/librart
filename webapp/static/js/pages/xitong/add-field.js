jQuery(function($){
    var form = $('#field-form');
    var fieldName = $('#fieldName');
    var fieldUnit = $('#fieldUnit');
    
    function validFieldName(){
        if($.trim(fieldName.val())!="") return true;
        alert("请输入字段名称");
        return false;
    }
    function validFieldUnit(){
    	if($.trim(fieldUnit.val())!="") return true;
        alert("请输入字段单位");
        return false;
    }
    
    form.on('submit',function(){
        return validFieldName()&&validFieldUnit();
    });

});
