$(function () {
    	
        $('.data').hide();
        
    	// Radialize the colors
		Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function(color) {
		    return {
		        radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
		        stops: [
		            [0, color],
		            [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
		        ]
		    };
		});
                
        var data = [];
        $('table.data tr').each(function(i, e){
            var el = $(e);
            data.push({name: el.find('.gName').text(), y: parseFloat(el.find('.val').text())});
        });
        
        var biggest = 0;
        var biggestIndex;
        for (var i in data){
            var d = data[i];
            if (biggest < d.y){
                biggest = d.y;
                biggestIndex = i;
            }
        }
        data[biggestIndex].sliced = true;
		// Build the chart
        $('#graph').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: 'Genre similarity'
            },
            tooltip: {
        	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        formatter: function() {
                            return '<b>'+ this.point.name +'</b>: '+ this.percentage +' %';
                        }
                    }
                }
            },
            series: [{
                type: 'pie',
                name: 'Genre similarity',
                data: data
            }]
        });
    });
    
