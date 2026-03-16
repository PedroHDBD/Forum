<template id='publicacao-template'>
	<div class='my-5 w-75 shadow rounded-3 publicacao bg-dark-subtle'>

		<div
			class=' p-3 m-0 bg-dark bg-gradient text-white rounded-3 rounded-bottom-0 fs-3 d-flex justify-content-between headerPublicacao'>
			<div class="card rounded-circle overflow-hidden cardImagemUsuario"
				style="width: 6vw; aspect-ratio: 1/1">
				<img class='img-fluid fotoPerfil'>
			</div>
			<span class='span-publicacao-autor mt-1 ms-2'></span> <span
				class='fw-normal m-0 flex-grow-1 ms-5 mt-3 float-end span-publicacao-data fs-6'></span>
		</div>

		<h5
			class='p-3 m-0 bg-dark-subtle fw-normal py-4 text-break publicacao-texto'></h5>
		<hr class='m-0'>

		<div class="w-50 h-25 mx-auto text-center">
			<img class="imagem img-fluid rounded-3 p-3">
		</div>

		<div class='bg-body-secondary px-3 pt-3 pb-2'>Comentários:</div>

		<div class='comentariosDiv p-1 bg-body-secondary'></div>

		<div class='adicionarComentario d-flex align-items-stretch w-100'>
			<form class='d-flex w-100 adicionarComentarioForm'>
				<input type='text' name='texto' maxlength='1000' required
					placeholder='Digite seu comentário...' autocomplete='off'
					class='p-2 border-dark border-opacity-50 flex-grow-1'
					style='border-radius: 0 0 0 0.375rem'>
				<button type='submit'
					class='btn-submit px-3 bg-info-subtle bg-gradient border border-black border-opacity-10 fs-6 custom-rounded adicionarComentarioButton'>
					<i class='bi bi-send fs-5'></i>
				</button>
			</form>
		</div>
	</div>
</template>


<template id='comentario-template'>
	<div
		class="comentario bg-body-tertiary border-top border-bottom border-start border-black border-opacity-10 p-3">

		<div class="row g-3 align-items-start">

			<div class="col-auto">
				<img class="imagemPerfilComentario rounded-circle"
					style="width: 4vw; aspect-ratio: 1/1; object-fit: cover;">
			</div>

			<div class="col">
				<div class="d-flex justify-content-between">
					<span class="comentarioUsername fw-bold"></span> <span
						class="comentarioData fw-light text-nowrap text-secondary ms-2"></span>
				</div>

				<div class="comentarioTexto text-break mt-1"></div>
			</div>

			<div class="col-auto">
				<div class="comentarioActions d-flex"></div>
			</div>

		</div>

	</div>
</template>

<template id='curtirComentarioTemplate'>
	<div class='d-flex curtirComentario'>
		<button type="submit"
			class="d-flex px-1 forumBtn fs-6 h-100 align-items-center curtirComentarioButton w-100 bg-body-tertiary">
			<span class="numLikesComentario fs-5 pe-2">0</span> <i
				class="bi bi-heart fs-5"></i>
		</button>
	</div>
</template>

<template id='excluirComentarioTemplate'>
	<div class='d-flex excluirComentario'>
		<button type="submit"
			class="d-flex ps-1 pe-3 forumBtn fs-6 h-100 align-items-center excluirComentarioButton w-100 bg-body-tertiary">
			<i class="bi bi-trash fs-5"></i>
		</button>
	</div>
</template>

<template id='acoesPublicacaoTemplate'>
	<div class='excluirPublicacao'>
		<button type='submit'
			class='p-2 border border-dark border-opacity-25 rounded-start-pill fs-6 bg-dark-subtle excluirPublicacaoButton ms-2'>
			<i class='bi bi-trash fs-5 px-1'></i>
		</button>
	</div>

	<div class='editarPublicacao'>
		<button type='submit'
			class='p-2 border border-dark border-opacity-25 rounded-end-pill fs-6 bg-dark-subtle editarPublicacaoButton'>
			<i class='bi bi-pencil fs-5 px-1'></i>
		</button>
	</div>
</template>

<template id='curtirPublicacaoTemplate'>
	<div class='curtirPublicacao'>
		<button type='submit'
			class='p-2 border border-dark border-opacity-25 rounded-pill fs-6 bg-dark-subtle curtirPublicacaoButton'>
			<span class='numLikes fs-5 px-1'></span> <i
				class='bi bi-heart fs-5 pe-1'></i>
		</button>
	</div>
</template>

<template id='acoesTopicoTemplate'>
	<div class='d-flex float-start me-2'>
		<div class='excluirTopico d-flex align-items-stretch'>
			<button type='submit'
				class='p-1 border border-dark border-opacity-25 rounded-start-pill fs-6 bg-dark-subtle excluirTopicoButton'>
				<i class='bi bi-trash fs-6 px-1'></i>
			</button>
		</div>

		<div class='editarTopico d-flex align-items-stretch'>
			<button type='submit'
				class='p-1 border border-dark border-opacity-25 rounded-end-pill fs-6 bg-dark-subtle editarTopicoButton'>
				<i class='bi bi-pencil fs-6 px-1'></i>
			</button>
		</div>
	</div>
</template>