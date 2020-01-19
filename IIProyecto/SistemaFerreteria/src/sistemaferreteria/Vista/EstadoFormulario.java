package sistemaferreteria.Vista;

public class EstadoFormulario {

    private Estado estadoActual;
    private Object registroActual;
    private boolean modificado;

    public EstadoFormulario(Estado estadoActual, Object registroActual, boolean modificado) {
        this.estadoActual = estadoActual;
        this.registroActual = registroActual;
        this.modificado = modificado;
    }

    public EstadoFormulario() {
        this(Estado.ESTADO_CONSULTA, null, false);
    }

    public Estado getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(Estado estadoActual) {
        this.estadoActual = estadoActual;
    }

    public Object getRegistroActual() {
        return registroActual;
    }

    public void setRegistroActual(Object registroActual) {
        this.registroActual = registroActual;
    }

    public boolean isModificado() {
        return modificado;
    }

    public void setModificado(boolean modificado) {
        this.modificado = modificado;
    }

    public void cambiarModoConsulta() {
        setEstadoActual(Estado.ESTADO_CONSULTA);
        setModificado(false);
    }

    public void cambiarModoAgregar() {
        setEstadoActual(Estado.ESTADO_AGREGAR);
        setModificado(false);
    }

    public void cambiarModoModificar() {
        setEstadoActual(Estado.ESTADO_MODIFICAR);
        setModificado(false);
    }

    public void cambiarModoBuscar() {
        setEstadoActual(Estado.ESTADO_BUSCAR);
        setModificado(false);
    }

    public boolean enModoConsulta() {
        return getEstadoActual() == Estado.ESTADO_CONSULTA;
    }

    public boolean enModoAgregar() {
        return getEstadoActual() == Estado.ESTADO_AGREGAR;
    }

    public boolean enModoActualizacion() {
        return getEstadoActual() == Estado.ESTADO_MODIFICAR;
    }

    public boolean enModoBusqueda() {
        return getEstadoActual() == Estado.ESTADO_BUSCAR;
    }

    public boolean puedeAgregar() {
        return enModoConsulta();
    }

    public boolean puedeModificar() {
        return enModoConsulta() && (getRegistroActual() != null);
    }

    public boolean puedeEliminar() {
        return ((enModoConsulta()) || (enModoActualizacion()))
                && getRegistroActual() != null;
    }

    public boolean puedeGuardar() {
        return isModificado();
    }

    public boolean puedeBuscar() {
        return enModoConsulta();
    }

    public boolean puedeCancelar() {
        return !enModoConsulta();
    }

    public boolean puedeEjecutar() {
        return enModoBusqueda();
    }
}
