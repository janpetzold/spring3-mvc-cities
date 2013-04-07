var app = {};
app.controls = {};
app.controller = {};

//log4javascript für Ausgabe von Meldungen in Firebug-Konsole ähnlich log4j
if (!window.log) {
	window.log = log4javascript.getLogger("main");
	window.log.addAppender(new log4javascript.BrowserConsoleAppender());
}

app.init = function() {
	log.debug("Javascript geladen.");

	$('form[name="searchcity"]').on("submit", app.controls.clickHandler);

	// init autocomplete for search
	app.controller.autocomplete();
	
	// init datatables
	app.controller.datatables();
};

/*
app.controls.clickHandler = function(event) {
	// log.debug("ClickHandler aktiv - Element " + $(this).attr("id"));
	if ($(this).attr("class") == "deleteLink") {
		event.preventDefault();
		app.controller.deleteItem($(this).attr("href"));
	}
};

app.controller.deleteItem = function(item) {
	log.debug("Lösche den Eintrag " + item);
	$.ajax({
		url : item,
		success : function() {
			log.debug("AJAX-Request erfolgreich - Stadt " + item + " wurde gelöscht");
		}
	});
};
*/

app.controller.searchItem = function(item) {
	log.debug("Suche nach Begriff " + item);
};

app.controller.autocomplete = function() {
	log.debug("Autocomplete ist aktiv");
	$("#term").autocomplete({
		source: function(request, response) {
          $.ajax({
            url: "/cities/city/search",
            data: request,
            dataType: "json",
            type: "POST",
            success: function(data){
                response(data);
            }
          });
        },
		minLength : 2
	});
};

app.controller.datatables = function() {
	$("#datatable").dataTable({
		"bJQueryUI" : true,
		"bInfo" : false,
		"bAutoWidth" : false,
		"bLengthChange" : false,
		"bFilter" : false,
		"bPaginate" : true,
		"bSort" : false,
		"aoColumns" : 
			[ { sWidth : '250px'}, {
				sWidth : '120px'}, {
				sWidth : '175px'}, {
				sWidth : '100px'}, {
				sWidth : '65px'} ]
	});
};

$(document).bind("ready", app.init);