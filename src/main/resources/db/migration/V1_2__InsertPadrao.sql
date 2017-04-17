INSERT into profile VALUES (1 , 'Administrador');

INSERT into user_account values(1,'Everton',null,'289160DB0D9F39F9AE1754C4EC9C16F90B50E32E09C5FB5481AE642B3D3D1A36','everton@sol7.com.br',1, true, true);

insert into module values (500, 'Administrativo','','fa fa-gear','',3,FALSE);


insert into module VALUES (default, 'Manutenção Usuário', '','fa fa-user','/view/user/listUser.xhtml?faces-redirect=true',10, true,500);

insert into module VALUES (default, 'Perfis', '','fa fa-unlock-alt','/view/profile/listProfile.xhtml?faces-redirect=true',10, true, 500);
insert into module VALUES (default, 'Módulos do Sistema', '','fa fa-cubes','/view/module/listModule.xhtml?faces-redirect=true',15, true, 500);
insert into module VALUES (default, 'Configurações do Sistema', '','fa fa-pencil-square-o','/view/config/listConfig.xhtml?faces-redirect=true',20, true, 500);


INSERT into profile_module values (1, 1);
INSERT into profile_module values (1, 2);
INSERT into profile_module values (1, 3);
INSERT into profile_module values (1, 4);

