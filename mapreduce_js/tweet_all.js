/* 
 * This file defines the mapreduce function to count the number of tweets 
 * in the certain area of Melbourne.
 * Use database tweet_db
 *
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */
 
//name:cycle_b1, map: Get all tweets in B1, reduce: _count
 function (doc) {
   var i,x;
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;
   var longitude=doc.coordinates.coordinates[0];
   var latitude=doc.coordinates.coordinates[1];
   if(longitude>x1 && longitude<=x2 && latitude>=y3 && latitude<y2){
       emit(doc.ccc_city,[longitude,latitude]);
    }
}

//name:cycle_b2, map: Get all tweets in B2, reduce: _count
 function (doc) {
   var i,x;
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;
   var longitude=doc.coordinates.coordinates[0];
   var latitude=doc.coordinates.coordinates[1];
   if(longitude>x2 && longitude<=x3 && latitude>=y3 && latitude<y2){
       emit(doc.ccc_city,[longitude,latitude]);
    }
}
//name:cycle_b3, map: Get all tweets in B3, reduce: _count
 function (doc) {
   var i,x;
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;
   var longitude=doc.coordinates.coordinates[0];
   var latitude=doc.coordinates.coordinates[1];
   if(longitude>x3 && longitude<=x4 && latitude>=y3 && latitude<y2){
       emit(doc.ccc_city,[longitude,latitude]);
    }
}
//name:cycle_b4, map: Get all tweets in B4, reduce: _count
 function (doc) {
   var i,x;
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;
   var longitude=doc.coordinates.coordinates[0];
   var latitude=doc.coordinates.coordinates[1];
   if(longitude>x4 && longitude<=x5 && latitude>=y3 && latitude<y2){
       emit(doc.ccc_city,[longitude,latitude]);
    }
}
//name:cycle_c1, map: Get all tweets in C1, reduce: _count
 function (doc) {
   var i,x;
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;
   var longitude=doc.coordinates.coordinates[0];
   var latitude=doc.coordinates.coordinates[1];
   if(longitude>x1 && longitude<=x2 && latitude>=y4 && latitude<y3){
       emit(doc.ccc_city,[longitude,latitude]);
    }
}
//name:cycle_c2, map: Get all tweets in C2, reduce: _count
 function (doc) {
   var i,x;
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;
   var longitude=doc.coordinates.coordinates[0];
   var latitude=doc.coordinates.coordinates[1];
   if(longitude>x2 && longitude<=x3 && latitude>=y4 && latitude<y3){
       emit(doc.ccc_city,[longitude,latitude]);
    }
}
//name:cycle_c3, map: Get all tweets in C3, reduce: _count
 function (doc) {
   var i,x;
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;
   var longitude=doc.coordinates.coordinates[0];
   var latitude=doc.coordinates.coordinates[1];
   if(longitude>x3 && longitude<=x4 && latitude>=y4 && latitude<y3){
       emit(doc.ccc_city,[longitude,latitude]);
    }
}
//name:cycle_c4, map: Get all tweets in C4, reduce: _count
 function (doc) {
   var i,x;
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;
   var longitude=doc.coordinates.coordinates[0];
   var latitude=doc.coordinates.coordinates[1];
   if(longitude>x4 && longitude<=x5 && latitude>=y4 && latitude<y3){
       emit(doc.ccc_city,[longitude,latitude]);
    }
}
//name:cycle_c5, map: Get all tweets in C5, reduce: _count
 function (doc) {
   var i,x;
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;
   var longitude=doc.coordinates.coordinates[0];
   var latitude=doc.coordinates.coordinates[1];
   if(longitude>x5 && longitude<=x6 && latitude>=y4 && latitude<y3){
       emit(doc.ccc_city,[longitude,latitude]);
    }
}
//name:cycle_d3, map: Get all tweets in D3, reduce: _count
 function (doc) {
   var i,x;
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;
   var longitude=doc.coordinates.coordinates[0];
   var latitude=doc.coordinates.coordinates[1];
   if(longitude>x3 && longitude<=x4 && latitude>=y5 && latitude<y4){
       emit(doc.ccc_city,[longitude,latitude]);
    }
}
//name:cycle_d4, map: Get all tweets in D4, reduce: _count
 function (doc) {
   var i,x;
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;
   var longitude=doc.coordinates.coordinates[0];
   var latitude=doc.coordinates.coordinates[1];
   if(longitude>x4 && longitude<=x5 && latitude>=y5 && latitude<y4){
       emit(doc.ccc_city,[longitude,latitude]);
    }
}
//name:cycle_d5, map: Get all tweets in D5, reduce: _count
 function (doc) {
   var i,x;
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;
   var longitude=doc.coordinates.coordinates[0];
   var latitude=doc.coordinates.coordinates[1];
   if(longitude>x5 && longitude<=x6 && latitude>=y5 && latitude<y4){
       emit(doc.ccc_city,[longitude,latitude]);
    }
}
