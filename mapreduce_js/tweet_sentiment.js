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

function (doc) {
   var date = doc.created_at;
   var weekend = ["Sat","Sun"];
   var x,i;

   for(i=0;i<weekend.length;i++){
     x=date.indexOf(weekend[i]);
     if(x!=-1 && doc.ccc_city=="Melbourne"){
       emit(doc.ccc_city,doc.created_at);
     }
   }
}