function TableTotals(table) {
    this.$table = table;
}

TableTotals.prototype.getStartColumn = function () {
    var result = Number(
        this.$table
            .find('thead tr:eq(0) th:eq(0)')
            .attr('colspan')
    );
    if(isNaN(result)){
        return 1;
    }
    return result;
};

var visibleOnly = function(idx, el) {
    return $(el).css('display') !== 'none';
};

TableTotals.prototype.findRowGroups = function () {
    var result = $(this.$table.find('tr > td[rowspan]').filter(visibleOnly));
    return result;
};

TableTotals.prototype.calculateTotalsForRows = function ($rows, startColumn, cellValueExtractor) {
    var result = [];
    $rows.each(function (rowIdx, currentRow) {
        $(currentRow).find('td:gt(' + (startColumn - 1) + ')').each(function (colIdx, currentCol) {
            if (typeof result[colIdx] === 'undefined') {
                result[colIdx] = 0;
            }
            result[colIdx] += cellValueExtractor($(currentCol));
        });
    });
    return result;
};

TableTotals.prototype.generateTableRowFor = function (result) {
    var rowHtml = "";
    for (i = 0; i < result.length; i++) {
        var obj = result[i];
        rowHtml += '<td>' + obj + '</td>';
    }
    return rowHtml;
};

TableTotals.prototype.insertTotalRowOnTable = function (result, rowGroup) {
    var startColumn = this.getStartColumn() - 1;
    var $row = this.$table.find('tbody > tr:eq(' + result.fromIndex + ')');
    var $cols = $row.find('td:gt(' + startColumn + ')');
    var columnsTotal = result.columnsTotal;
    $cols.each(function(idx, val) {
        var $col = $(val);
        var $label = $col.find('label');
        if($label.hasClass('total-sum')){
            $label.html(accounting.formatMoney(columnsTotal[idx],"",2, ".",","));
        }
        else if($label.hasClass('total-avg')){
            $label.html(accounting.formatMoney((columnsTotal[idx])/(rowGroup.rowSpan - 1),"",2, ".",","));
        }
        else if($label.hasClass('total-count')){
            $label.html(accounting.formatMoney((rowGroup.rowSpan - 1),"",0));
        }else if ($label.hasClass('total-calcule')){
            var $calcCell = $('.calcule:eq('+idx+')');
            var formula = $calcCell.text();


            var exp = new ExpressionFactory(formula);
            var varValues = {};

            exp.getVars().forEach(function (el) {
                // lookup tabela premissas

                var listVar = $('.variable-name');
                for (i = 0; i < listVar.length && (idx - i) >= 0; i++) {
                    if(el == '#' + $('.variable-name:eq('+(idx - i)+')').text()){
                        if($('.variable-format:eq('+(idx - i)+')').text() == 'PERCENTUAL'){
                            varValues[el] = columnsTotal[idx-i]/100;
                        }else{
                            varValues[el] = columnsTotal[idx-i];
                        }
                        break;
                    }
                }
            });
            if($('.variable-format:eq('+(idx)+')').text() == 'PERCENTUAL'){
                $label.html((accounting.formatMoney(exp.evaluate(varValues)*100,"",2, ".",",")+' %'));
            }else{
                $label.html((accounting.formatMoney(exp.evaluate(varValues),"",2, ".",",")));
            }

        }
    });
};

TableTotals.prototype.insertTotalAll = function (result, rowGroup) {
    var startColumn = this.getStartColumn() - 1;
    var $row = this.$table.find('tbody > tr:eq(' + result.fromIndex + ')');
    var $rows = this.$table.find('tbody > tr:gt(' + 0 + ')');

    $rows = $rows.filter(function () {
        return $(this).find("td .aggregated").length === 0;
    });
    var $cols = $row.find('td:gt(' + startColumn + ')');
    var columnsTotal = result.columnsTotal;
    $cols.each(function(idx, val) {
        var $col = $(val);
        var $label = $col.find('label');
        if($label.hasClass('total-sum')){
            $label.html(accounting.formatMoney(columnsTotal[idx],"",2, ".",","));
        }
        else if($label.hasClass('total-avg')){
            $label.html(accounting.formatMoney((columnsTotal[idx])/$rows.length,"",2, ".",","));
        }
        else if($label.hasClass('total-count')){
            $label.html($rows.length);
        }else if ($label.hasClass('total-calcule')){
            var $calcCell = $('.calcule:eq('+idx+')');
            var formula = $calcCell.text();


            var exp = new ExpressionFactory(formula);
            var varValues = {};

            exp.getVars().forEach(function (el) {
                // lookup tabela premissas

                var listVar = $('.variable-name');
                for (i = 0; i < listVar.length && (idx - i) >= 0; i++) {
                    if(el == '#' + $('.variable-name:eq('+(idx - i)+')').text()){
                        if($('.variable-format:eq('+(idx - i)+')').text() == 'PERCENTUAL'){
                            varValues[el] = columnsTotal[idx-i]/100;
                        }else{
                            varValues[el] = columnsTotal[idx-i];
                        }
                        break;
                    }
                }
            });
            if($('.variable-format:eq('+(idx)+')').text() == 'PERCENTUAL'){
                $label.html((accounting.formatMoney(exp.evaluate(varValues)*100,"",2, ".",",")+' %'));
            }else{
                $label.html((accounting.formatMoney(exp.evaluate(varValues),"",2, ".",",")));
            }

        }
    });
};


function generateTotal() {
    //var tableComponent = new TableTotals($('.dataTables_scrollBody').find('table'));
    var tableComponent = new TableTotals($('.datatable').find('table'));
    var groups = tableComponent.findRowGroups();
    for (var i = 0; i < groups.length; i++) {
        var rowGroup = groups[i];
        var result = tableComponent.generateTotalRowForGroup(rowGroup);
        tableComponent.insertTotalRowOnTable(result, rowGroup);
    }
    generateAllTotal();
}

function generateAllTotal() {
    //var tableComponent = new TableTotals($('.dataTables_scrollBody').find('table'));
    var tableComponent = new TableTotals($('.datatable').find('table'));
    var groups = tableComponent.findRowGroups();
    if(groups.length ==0){
        var rowGroup = $('.datatable').find('table').find('tr > td:first-child').filter(visibleOnly);
        result = tableComponent.generateAllTotal(rowGroup);
        tableComponent.insertTotalAll(result, rowGroup);
    }else{
        for (var i = 0; i < groups.length; i++) {
            var rowGroup = groups[i];
            var result = tableComponent.generateAllTotal(rowGroup);
            tableComponent.insertTotalAll(result, rowGroup);
        }
    }
}

TableTotals.prototype.generateTotalRowForGroup = function (rowGroup) {
    var $rowGroup = $(rowGroup);
    var startAtColumn = this.getStartColumn();
    var rowspan = Number($rowGroup.attr('rowspan'));
    var $tr = $rowGroup.parent();

    var fromIndex = $tr.index();
    var toIndex = fromIndex + rowspan;

    var $tbody = $tr.parent();
    var $rowGroupToProcess = $tbody.find('tr').slice(fromIndex, toIndex);
    var result = this.calculateTotalsForRows($rowGroupToProcess, startAtColumn, function ($td) {
        var value = Number($td.find('label:eq(0)').text());
        return isNaN(value) ? 0 : value;
    });
    return {
        $rowGroup: $rowGroup,
        fromIndex: fromIndex,
        toIndex: toIndex,
        columnsTotal: result
    };
};

TableTotals.prototype.generateAllTotal = function (rowGroup) {
    var $rowGroup = $(rowGroup);
    var startAtColumn = this.getStartColumn();
    var $tr = $rowGroup.parent();
    var $tbody = $tr.parent();
    var $rowGroupToProcess = $tbody.find('tr');

    var result = this.calculateTotalsForRows($rowGroupToProcess, startAtColumn, function ($td) {
        var value = Number($td.find('label:eq(0)').text());
        return isNaN(value) ? 0 : value;
    });
    return {
        $rowGroup: $rowGroup,
        fromIndex: 0,
        toIndex: 8000,
        columnsTotal: result

    };
};

var AnalysisLoader = function ($container) {
    var width = $container.width()-50;
    var height = $container.height()-50;
    var url = $container.data('url');
    url += '&width='+width;
    url += '&height='+height;
    $container.html('<iframe frameborder="0" align="center" style="width: 98%; height: 98%" src="'+url+'"></iframe>');
};

var ExpressionFactory = (function (){

    var regex = /#(\S+)/g;

    var ExpressionFactory = function(exp) {
        this.exp = exp;
    };

    ExpressionFactory.prototype = {
        getExp: function () {
            return this.exp;
        },
        getVars: function () {
            var result = this.exp.match(regex);
            return result !== null ? result : [];
        },
        replace: function (obj) {
            var result = this.exp;
            for(var key in obj) {
                result = result.replace(key, obj[key]);
            }
            return result;
        },
        evaluate: function (obj) {
            try {
                return eval(this.replace(obj));
            }catch (e){
                //console.log(this.replace(obj));
            }
        }
    };
    return ExpressionFactory;
})();