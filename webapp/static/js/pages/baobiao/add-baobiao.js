jQuery(function($){
    $(".datepicker").datepicker({
        altFormat: "yyyymmdd",
        dateFormat: "yymmdd"
    });
    
    var addForm = $('#BAOBIAO-JOB-FORM');
    var projectId = $('#projectId');
    var startTime = $('#startTime');
    var endTime = $('#endTime');
    var reportId = $('#billName');
    var billBelongTime = $('#billBelongTime');
    
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
    var a = /^(\d{4})(\d{2})$/;
    function validBillBelongTime(){
    	if($.trim(billBelongTime.val())==""){
    		 alert("请填写账单归属时间");
    		 return false;
    	}
    	if (!a.test(billBelongTime.val())){
    		 alert("账单归属时间格式填写不正确");
    		 return false;
    	}
        return true;
    }
    function validReport(){
    	if($.trim(reportId.val())!="") return true;
        alert("填写项目账单名称");
        return false;
    }
    
    addForm.on('submit',function(){
        return validProject()&&validStartTime()&&validEndTime()&&validBillBelongTime()&&validReport();
    });
});
