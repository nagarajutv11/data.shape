URL: http://localhost:8080/shape
Body: (JSON)
{
	"data":[
		{
			"name": "Movie1",
			"Date": "11-Jan-2010",
			"Summary": "eerewrwrwr",
			"Cast": ["x", "Y", "Z"]
		},
		{
			"name": "Movie2",
			"Date": "12-Jan-2020",
			"Summary": "tyuytutyutyu",
			"Cast": ["A", "B", "C"]
		},
		{
			"name": "Movie3",
			"Date": "13-Jan-2020",
			"Summary": "123233123",
			"Cast": ["1", "2", "3"]
		}
	],
	"template": [
		{
			"name": "main",
			"type": "split",
			"exp": "$element",
			"next": "Add Count"
		},
		{
			"name":"Add Count",
			"type": "map",
			"add": {
				"CastCount": "$size(Cast)"
			},
			"next": "create"
		},
		{
			"name":"create",
			"type": "map",
			"only": {
				"Date": "$date_parse(Date, 'dd-MMM-yyyy')",
				"Desc": "name + ' (' + Date + ') - ' + Summary",
				"Name": "name"
			},
			"next": "check this month date"
		},
		{
			"name":"check this month date",
			"type": "branch",
			"if": "$after(Date, $this_month)",
			"then": "add valid date true",
			"else": "add valid date false"
		},
		{
			"name": "add valid date true",
			"type": "map",
			"add": {
				"valid": "true"
			},
			"next": "filter"
		},
		{
			"name": "add valid date false",
			"type": "map",
			"add": {
				"valid": "false"
			},
			"next": "collect"
		},
		{
			"name": "filter",
			"type": "filter",
			"exp": "$after(Date, $this_month)",
			"next": "collect"
		},
		{
			"name": "collect",
			"type": "collect",
			"exp": "$per_request",
			"next": "send"
		},
		{
			"name":"send",
			"type": "distribute",
			"url": "http://localhost:8080/print",
			"next": "end"
		}
	]
}