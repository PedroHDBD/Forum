<template id='publicacao-template'>
	<div class='publicacao bg-dark-subtle rounded-3 shadow h-100 d-flex flex-column'>

		<!-- HEADER -->
		<div class='p-3 bg-dark bg-gradient text-white d-flex align-items-center justify-content-between rounded-top'>

			<div class="d-flex align-items-center gap-2">

				<img class='fotoPerfil rounded-circle'
					style="width: 48px; height: 48px; object-fit: cover;">

				<div class="d-flex flex-column">
					<span class='span-publicacao-autor fw-semibold'></span>
					<span class='span-publicacao-data text-light small'></span>
				</div>

			</div>

			<div class="d-flex align-items-center headerPublicacao"></div>
		</div>

		<div class='p-3'>
			<p class='m-0 publicacao-texto text-break'></p>
		</div>

		<!-- IMAGEM -->
		<div class="px-3 pb-3 text-center">
			<img class="imagem img-fluid rounded-3" style="max-height: 400px; object-fit: cover;">
		</div>

		<hr class='m-0'>

		<!-- COMENTÁRIOS -->
		<div class='bg-body-secondary px-3 py-2 small fw-semibold'>
			Comentários:
		</div>

		<div class='comentariosDiv bg-body-secondary'></div>

		<div class='adicionarComentario'>
			<form class='d-flex w-100 adicionarComentarioForm'>

				<input type='text'
					name='texto'
					maxlength='1000'
					required
					placeholder='Digite seu comentário...'
					autocomplete='off'
					class='form-control rounded-0 border-0'>

				<button type='submit'
					class='btn btn-info px-3'>
					<i class='bi bi-send'></i>
				</button>

			</form>
		</div>

	</div>
</template>


<template id='comentario-template'>
	<div class="comentario bg-body-tertiary border-top p-3">

		<div class="d-flex gap-2">

			<img class="imagemPerfilComentario rounded-circle"
				style="width: 36px; height: 36px; object-fit: cover;">

			<div class="flex-grow-1">

				<div class="d-flex justify-content-between flex-wrap">
					<span class="comentarioUsername fw-bold"></span>
					<span class="comentarioData text-secondary small"></span>
				</div>

				<div class="comentarioTexto text-break mt-1"></div>

			</div>

			<div class="comentarioActions d-flex align-items-start"></div>

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
			class='p-1 p-md-2 border border-dark border-opacity-25 rounded-start-pill bg-dark-subtle excluirPublicacaoButton ms-1 ms-md-2'>
			<i class='bi bi-trash fs-6 fs-md-5 px-1'></i>
		</button>
	</div>

	<div class='editarPublicacao'>
		<button type='submit'
			class='p-1 p-md-2 border border-dark border-opacity-25 rounded-end-pill bg-dark-subtle editarPublicacaoButton'>
			<i class='bi bi-pencil fs-6 fs-md-5 px-1'></i>
		</button>
	</div>
</template>

<template id='curtirPublicacaoTemplate'>
	<div class='curtirPublicacao'>
		<button type='submit'
			class='p-1 p-md-2 border border-dark border-opacity-25 rounded-pill fs-6 bg-dark-subtle curtirPublicacaoButton'>
			<span class='numLikes fs-6 px-1'></span>
			<i class='bi bi-heart fs-6 fs-md-5'></i>
		</button>
	</div>
</template>

<template id='acoesTopicoTemplate'>
	<div class='d-flex align-items-center me-2'>

		<button type='button'
			class='p-1 p-md-2 border border-dark border-opacity-25 rounded-start-pill bg-dark-subtle excluirTopicoButton'>
			<i class='bi bi-trash fs-6 fs-md-5'></i>
		</button>

		<button type='button'
			class='p-1 p-md-2 border border-dark border-opacity-25 rounded-end-pill bg-dark-subtle editarTopicoButton'>
			<i class='bi bi-pencil fs-6 fs-md-5'></i>
		</button>

	</div>
</template>