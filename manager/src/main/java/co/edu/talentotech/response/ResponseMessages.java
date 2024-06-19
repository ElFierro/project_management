package co.edu.talentotech.response;

public class ResponseMessages {
    public static final String CODE_200 = "200";
    public static final String CODE_400 = "400";
    public static final String CODE_404 = "404";
    public static final String CODE_500 = "500";
    
    public static final String ERROR_404 = "El servidor no pudo encontrar el contenido solicitado";
    public static final String ERROR_400 = "Error de esquema en el mensaje XML";
    public static final String ERROR_200 = "Exito";
    
    public static final String ERROR_ID= "El campo id no es necesario en la peticion.";
    public static final String ERROR_ID_REQUIRED= "El valor del id es requerido en el cuerpo de petición";
    public static final String ERROR_NAME = "El nombre es un valor obligatorio y no debe sobrepasar los 100 caracteres.";
    public static final String ERROR_ASSIGNED = "El nombre de la persona asignada no debe ser nayor a 100 caracteres";
    public static final String ERROR_MANAGER = "El nombre deL responsable del proyecto no debe ser nayor a 100 caracteres";
    public static final String ERROR_DESCRIPTION = "La descripción no debe sobrepasar los 250 caracteres.";
    public static final String ERROR_FORMAT_DATE = "El formato de la fecha es incorrecto, debe de ser 'aaaa-mm-dd'";
    public static final String ERROR_STATUS = "Los estados validos son: 'Pendiente', 'En progreso' o 'Completado'";
    public static final String ERROR_STATUS_REQUIRED = "El estatus es obligatorio";
    public static final String ERROR_PROJECT_REQUIRED = "Se debe asignar a un proyecto";
    public static final String ERROR_PROJECT_INVALID = "El proyecto asignado no existe";
    public static final String ERROR_NOT_EXIST = "El recurso no existe"; 
    public static final String ERROR_EMAIL = "El formato del correo asignado es inválido"; 
    public static final String ERROR_STATUS_PROJECT = "Los estados validos son: 'Inicio', 'Planificación', 'Ejecución', 'Supervisión' o 'Cierre'";
}