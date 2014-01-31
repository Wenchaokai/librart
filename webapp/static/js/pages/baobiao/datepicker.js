jQuery(function($){
	if($("#startTime")){
		$("#startTime").datepicker({
			altFormat: "yyyymmdd",
			appendText:"yyyymmdd",
			dateFormat: "yymmdd"
		});
	}

    if($("#endTime")){
		$("#endTime").datepicker({
			altFormat: "yyyymmdd",
			appendText:"yyyymmdd",
			dateFormat: "yymmdd"
		});
	}
});
