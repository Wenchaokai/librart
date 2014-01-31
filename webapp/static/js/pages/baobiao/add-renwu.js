jQuery(function($){
    $(".datepicker").datepicker({
        altFormat: "yyyymmdd",
        dateFormat: "yymmdd"
    });
    
    var addForm = $('#BAOBIAO-JOB-FORM');
    var projectId = $('#projectId');
    var startTime = $('#startTime');
    var endTime = $('#endTime');
    var reportId = $('#reportId');
    
    function validProject(){
    	 var checkValue=projectId.val(); 
        if(checkValue!=-1) return true;
        alert("请选择项目");
        return false;
    }
    function validStartTime(){
    	if($.trim(startTime.val())!="") return true;
        alert("请选择开始时间");
        return false;
    }
    function validEndTime(){
    	if($.trim(endTime.val())!="") return true;
        alert("请选择结束时间");
        return false;
    }
    function validReport(){
    	var checkValue=reportId.val();
        if(checkValue>0) return true;
        alert("请选择项目对应的模板(项目可能没有模板，该项目不能添加报表任务)");
        return false;
    }
    
    addForm.on('submit',function(){
        return validProject()&&validStartTime()&&validEndTime()&&validReport();
    });
});
