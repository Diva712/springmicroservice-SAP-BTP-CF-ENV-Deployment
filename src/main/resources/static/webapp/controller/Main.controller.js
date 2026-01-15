sap.ui.define([
        'sap/ui/core/mvc/Controller',
            'sap.ui/model/odata/v2/ODataModel'
            ], function(Controller,ODataModel){
                'use strict';

                    return Controller.extend("donald.controller.Main",{
                            //class have a constructor
                                    onInit: function(){
                                                //create our model object of type odata used based on current relative path
                                                            var ODataModel = new ODataModel("odata-service-rul(/anubhav.svc)");
                                                                        
                                                                                    //set the model object at application lvl
                                                                                                sap.ui.getCore().setModel(ODataModel);

                                                                                                        }

                                                                                                            });
                                                                                                            })
])