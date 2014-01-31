jQuery(function($){
    var form = $('#subject-form');
    var subjectName = $('#subjectName');
    
    function validSubjectName(){
        if($.trim(subjectName.val())!="") return true;
        alert("请输入科目名称");
        return false;
    }
    
    form.on('submit',function(){
        return validSubjectName();
    });
    

});
