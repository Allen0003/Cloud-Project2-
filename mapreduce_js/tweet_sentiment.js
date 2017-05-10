/* 
 * This file defines the mapreduce function to get all tweets created on weekday 
 * or weekend in Melbourne
 * Use database black_spots_2007-2012_vic
 *
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */

//name:mel_mon, map: Get all tweets created on Monday, reduce: none
function (doc) {
   var date = doc.created_at;
   var weekday = ["Mon"];
   var x,i;

   for(i=0;i<weekday.length;i++){
     x=date.indexOf(weekday[i]);
     if(x!=-1 && doc.ccc_city=="Melbourne"){
       emit(doc.ccc_city,doc.created_at);
     }
   }
}

//name:mel_tue, map: Get all tweets created on Tuesday, reduce: none
function (doc) {
   var date = doc.created_at;
   var weekday = ["Tue"];
   var x,i;

   for(i=0;i<weekday.length;i++){
     x=date.indexOf(weekday[i]);
     if(x!=-1 && doc.ccc_city=="Melbourne"){
       emit(doc.ccc_city,doc.created_at);
     }
   }
}

//name:mel_wed, map: Get all tweets created on Wednesday, reduce: none
function (doc) {
   var date = doc.created_at;
   var weekday = ["Wed"];
   var x,i;

   for(i=0;i<weekday.length;i++){
     x=date.indexOf(weekday[i]);
     if(x!=-1 && doc.ccc_city=="Melbourne"){
       emit(doc.ccc_city,doc.created_at);
     }
   }
}

//name:mel_thu, map: Get all tweets created on Thursday, reduce: none
function (doc) {
   var date = doc.created_at;
   var weekday = ["Thu"];
   var x,i;

   for(i=0;i<weekday.length;i++){
     x=date.indexOf(weekday[i]);
     if(x!=-1 && doc.ccc_city=="Melbourne"){
       emit(doc.ccc_city,doc.created_at);
     }
   }
}

//name:mel_fri, map: Get all tweets created on Friday, reduce: none
function (doc) {
   var date = doc.created_at;
   var weekday = ["Mon","Tue","Wed","Thu","Fri"];
   var x,i;

   for(i=0;i<weekday.length;i++){
     x=date.indexOf(weekday[i]);
     if(x!=-1 && doc.ccc_city=="Melbourne"){
       emit(doc.ccc_city,doc.created_at);
     }
   }
}

//name:mel_sat, map: Get all tweets created on Saturday, reduce: none
function (doc) {
   var date = doc.created_at;
   var weekend = ["Sat"];
   var x,i;

   for(i=0;i<weekday.length;i++){
     x=date.indexOf(weekday[i]);
     if(x!=-1 && doc.ccc_city=="Melbourne"){
       emit(doc.ccc_city,doc.created_at);
     }
   }
}

//name:mel_sat, map: Get all tweets created on Sunday, reduce: none
function (doc) {
   var date = doc.created_at;
   var weekend = ["Sun"];
   var x,i;

   for(i=0;i<weekend.length;i++){
     x=date.indexOf(weekend[i]);
     if(x!=-1 && doc.ccc_city=="Melbourne"){
       emit(doc.ccc_city,doc.created_at);
     }
   }
}