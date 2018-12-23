function ready(toogleID){
  if(document.getElementById(toogleID).style.display == "block"){
    document.getElementById(toogleID).style.display = "none"
  }else{
    document.getElementById(toogleID).style.display = "block"
  }
}
