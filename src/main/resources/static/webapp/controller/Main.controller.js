sap.ui.define([
    'sap/ui/core/mvc/Controller',
    'sap/ui/model/odata/v2/ODataModel',
    'sap/ui/model/json/JSONModel',
    'sap/m/MessageBox'
], function (Controller, ODataModel, JSONModel, MessageBox) {
    "use strict";

    return Controller.extend("donald.controller.Main", {

        onInit: function () {
            //Create our Model object of type odata based on current relative path
            var oDataModel = new ODataModel("/anubhav.svc/");

            //Set the model objet at application level
            this.getView().setModel(oDataModel);

            var oModel = new JSONModel({
                data:{
                    Code: "",
                    CompanyName: "",
                    FirstName: "",
                    LastName: "",
                    Website: "",
                    Email: "",
                    Status: 1,
                    RegDate: new Date()
                }
            });
            this.getView().setModel(oModel, "local");

        },

        onCreate: function(){
            //Get  the data from json model -- equal to payload
            var oModel = this.getView().getModel("local");
            var payload = oModel.getProperty("/data");
            //get Odata model object
            var oDataModel = this.getView().getModel();
            //Fire create = POST operation on 
            oDataModel.create("/VendorSet", payload, {
                success: function(data){
                    MessageBox.confirm("Your vendor is created with code " + data.Code);
                },
                error:function(){
                    MessageBox.error("Oops ! Something went wrong")
                }
            })
        }

    });
});
