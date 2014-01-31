jQuery(function($){
    var addForm = $('#project-form');
    var projectName = $('#projectName');
    var projectCooper = $('#projectCooper');
    var projectBusiness = $('#projectBusiness');
    
    function validProjectName(){
        if($.trim(projectName.val())!="") return true;
        alert("请输入项目名称");
        return false;
    }
    function validProjectCooper(){
    	if($.trim(projectCooper.val())!="") return true;
        alert("请输入项目合作方式");
        return false;
    }
    function validProjectBusiness(){
    	if($.trim(projectBusiness.val())!="") return true;
        alert("请输入项目优库业务");
        return false;
    }
    
    addForm.on('submit',function(){
        return validProjectName()&&validProjectCooper()&&validProjectBusiness();
    });

});
