/*
 * Support plugin to sort the numeric cols
 * which are having symbols(+,-) as the data
 *
 */
jQuery.extend(jQuery.fn.dataTableExt.oSort, {
    "signed-num-pre" : function(a) {
        a = a.toString();
        return (a == "-" || a === "") ? 0 : a.replace('+', '') * 1;
    },

    "signed-num-asc" : function(a, b) {
        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
    },

    "signed-num-desc" : function(a, b) {
        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
    }
});
/*
 * Support plugin to sort the Date
 * format: dd-mmm-yyyy hh:mm a
 *
 */
jQuery.extend(jQuery.fn.dataTableExt.oSort, {

    "us-date-pre" : function(a) {
        a = a.replace("<br>", " ").replace("<span>", "");
        a = a.replace("</span>", "");
        var ele = $(a);
        if(ele !== undefined && ele.val() !== undefined){
            a = ele.val();
        }
        return (a === "") ? 0 : (moment(a).unix() * 1000);

    },

    "us-date-asc" : function(a, b) {
        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
    },

    "us-date-desc" : function(a, b) {
        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
    }
});

