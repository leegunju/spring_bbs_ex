async function removeFileToServer(uuid){
    try{
        const url = '/board/file/'+uuid;
        const config ={
            method: 'delete'
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    }catch(err){
        console.log(err);
    }
}

document.addEventListener('click', (e)=>{
    if(e.target.classList.contains('file-x')){
        let uuid = e.target.dataset.uuid;
        console.log(uuid);
        //비동기로 데이터 삭제
        removeFileToServer(uuid).then(result =>{
            console.log(result);
            alert('파일삭제'+(parseInt(result) >0? '성공' : '실패'));
            if(result > 0){
                e.target.closest('li').remove();
            }
        })
    }
})