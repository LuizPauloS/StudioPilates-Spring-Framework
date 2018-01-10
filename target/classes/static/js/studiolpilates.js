$('#confirmaExclusaoPaciente').on(
		'show.bs.modal',
		function(event) {
			var button = $(event.relatedTarget);
			var idPaciente = button.data('id');
			var nomePaciente = button.data('nome');

			var modal = $(this);
			var form = modal.find('form');
			var action = form.data('url-base');
			if (!action.endsWith('/')) {
				action += '/';
			}
			form.attr('action', action + idPaciente);

			modal.find('.modal-body span').html(
					'Tem certeza que deseja excluir o registro do paciente <strong>'
							+ nomePaciente + '</strong> ?');
		});

$('#confirmaExclusaoPagamento').on(
		'show.bs.modal',
		function(event) {
			var button = $(event.relatedTarget);
			var idPaciente = button.data('id');
			var nomePaciente = button.data('nome');

			var modal = $(this);
			var form = modal.find('form');
			var action = form.data('url-base');
			if (!action.endsWith('/')) {
				action += '/';
			}
			form.attr('action', action + idPaciente);

			modal.find('.modal-body span').html(
					'Tem certeza que deseja excluir o registro do pagamento do cliente <strong>'
							+ nomePaciente + '</strong> ?');
		});

$(function() {
	$('[rel="tooltip"]').tooltip();
	$('.js-currency').maskMoney({
		decimal : ',',
		thousands : '.',
		allowZero : false,
		prefix : 'R$ ',
		affixesStay : false,
		allowNegative : true
	});
	$('.js-phone').mask("(99) 99999-9999");
	$('.js-cep').mask("99999-999");
	$('.js-date').mask("99/99/9999");
	//Efetua ativacao de paciente
	$('.js-atualiza-status').on('click',function(event) {
				event.preventDefault();

				var botaoAtivar = $(event.currentTarget);
				var urlAtivar = botaoAtivar.attr('href');

				var response = $.ajax({
					url : urlAtivar,
					type : 'PUT'
				});

				response.done(function(e) {
					var idAtivar = botaoAtivar.data('id');
					$('[data-role=' + idAtivar + ']').html(
							'<span class="label label-success">' + e
									+ '</span>');
					botaoAtivar.hide();
				});

				response.fail(function(e) {
					console.log(e);
					alert('Erro ao ativar registro do paciente!');
				});

				console.log('urlAtivar', urlAtivar);
			});
	//Efetua pagamento
	$('.js-efetua-pagamento').on(
			'click',
			function(event) {
				event.preventDefault();

				var botaoPagar = $(event.currentTarget);
				var urlPagar = botaoPagar.attr('href');

				var response = $.ajax({
					url : urlPagar,
					type : 'PUT'
				});

				response.done(function(e) {
					var idPagar = botaoPagar.data('id');
					$('[data-role=' + idPagar + ']').html(
							'<span class="label label-info">' + e + '</span>');
					botaoPagar.hide();
				});

				response.fail(function(e) {
					console.log(e);
					alert('Erro ao registrar pagamento do paciente '
							+ nomePaciente + '!');
				});

				console.log('urlAtivar', urlAtivar);
			});
});

$(document).ready(
		function() {

			function limpa_formulário_cep() {
				// Limpa valores do formulário de cep.
				$("#rua").val("");
				$("#bairro").val("");
				$("#cidade").val("");
				$("#uf").val("");
				$("#ibge").val("");
			}

			// Quando o campo cep perde o foco.
			$("#cep").blur(
					function() {

						// Nova variável "cep" somente com dígitos.
						var cep = $(this).val().replace(/\D/g, '');

						// Verifica se campo cep possui valor informado.
						if (cep != "") {

							// Expressão regular para validar o CEP.
							var validacep = /^[0-9]{8}$/;

							// Valida o formato do CEP.
							if (validacep.test(cep)) {

								// Preenche os campos com "..." enquanto
								// consulta webservice.
								$("#rua").val("...");
								$("#bairro").val("...");
								$("#cidade").val("...");
								$("#estado").val("...");
								$("#ibge").val("...");

								// Consulta o webservice viacep.com.br/
								$.getJSON("//viacep.com.br/ws/" + cep
										+ "/json/?callback=?", function(dados) {

									if (!("erro" in dados)) {
										// Atualiza os campos com os valores da
										// consulta.
										$("#rua").val(dados.logradouro);
										$("#bairro").val(dados.bairro);
										$("#cidade").val(dados.localidade);
										$("#estado").val(dados.uf);
										$("#ibge").val(dados.ibge);
									} // end if.
									else {
										// CEP pesquisado não foi encontrado.
										limpa_formulário_cep();
										alert("CEP não encontrado.");
									}
								});
							} // end if.
							else {
								// cep é inválido.
								limpa_formulário_cep();
								alert("Formato de CEP inválido.");
							}
						} // end if.
						else {
							// cep sem valor, limpa formulário.
							limpa_formulário_cep();
						}
					});
		});

jQuery(document).ready(function($) {
	$(".link-row").click(function() {
		window.location = $(this).attr('href');
	});
});

$(function() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
});

$('table.paginated').each(
		function() {
			var currentPage = 0;
			var numPerPage = 10;
			var $table = $(this);
			$table.bind('repaginate', function() {
				$table.find('tbody tr').hide().slice(currentPage * numPerPage,
						(currentPage + 1) * numPerPage).show();
			});
			$table.trigger('repaginate');
			var numRows = $table.find('tbody tr').length;
			var numPages = Math.ceil(numRows / numPerPage);
			var $pager = $('<div class="pager"></div>');
			for (var page = 0; page < numPages; page++) {
				$('<span class="page-number"></span>').text(page + 1).bind(
						'click',
						{
							newPage : page
						},
						function(event) {
							currentPage = event.data['newPage'];
							$table.trigger('repaginate');
							$(this).addClass('active').siblings().removeClass(
									'active');
						}).appendTo($pager).addClass('clickable');
			}
			$pager.insertBefore($table).find('span.page-number:first')
					.addClass('active');
		});
