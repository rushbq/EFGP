package com.prokits.custom.sb;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.Map;

import javax.naming.Context;

import com.dsc.nana.services.SecurityHandler;
import com.dsc.nana.services.SecurityHandlerHome;
import com.dsc.nana.services.SystemConfigManager;
import com.dsc.nana.services.SystemConfigManagerHome;
import com.dsc.nana.services.doc_manager.DocManager;
import com.dsc.nana.services.doc_manager.DocManagerFactory;
import com.dsc.nana.services.doc_manager.DocManagerFactoryHome;
import com.dsc.nana.services.doc_manager.DocManagerHome;
import com.dsc.nana.services.engine.PerformWorkItemHandler;
import com.dsc.nana.services.engine.PerformWorkItemHandlerHome;
import com.dsc.nana.services.engine.ProcessAbortController;
import com.dsc.nana.services.engine.ProcessAbortControllerHome;
import com.dsc.nana.services.engine.ProcessDispatcher;
import com.dsc.nana.services.engine.ProcessDispatcherHome;
import com.dsc.nana.services.engine.ProcessInstanceDTOFactory;
import com.dsc.nana.services.engine.ProcessInstanceDTOFactoryHome;
import com.dsc.nana.services.engine.ProcessPackageManager;
import com.dsc.nana.services.engine.ProcessPackageManagerHome;
import com.dsc.nana.services.engine.StatefulProcessDispatcher;
import com.dsc.nana.services.engine.StatefulProcessDispatcherHome;
import com.dsc.nana.services.engine.StatefulProcessDispatcherLocal;
import com.dsc.nana.services.exception.ServiceException;
import com.dsc.nana.services.form.FormCategoryManager;
import com.dsc.nana.services.form.FormCategoryManagerHome;
import com.dsc.nana.services.form.FormDTOFactory;
import com.dsc.nana.services.form.FormDTOFactoryHome;
import com.dsc.nana.services.form.FormDefinitionManager;
import com.dsc.nana.services.form.FormDefinitionManagerHome;
import com.dsc.nana.services.listreader.ListReaderFacade;
import com.dsc.nana.services.listreader.ListReaderFacadeHome;
import com.dsc.nana.services.listreader.PageListReaderFacade;
import com.dsc.nana.services.listreader.PageListReaderFacadeHome;
import com.dsc.nana.services.organization.ImportOrganization;
import com.dsc.nana.services.organization.ImportOrganizationHome;
import com.dsc.nana.services.organization.OrganizationManager;
import com.dsc.nana.services.organization.OrganizationManagerHome;
import com.dsc.nana.services.sysintegration.appform.AppFormManager;
import com.dsc.nana.services.sysintegration.appform.AppFormManagerHome;
import com.dsc.nana.services.sysintegration.mcloud.McloudManager;
import com.dsc.nana.services.sysintegration.mcloud.McloudManagerHome;
import com.dsc.nana.services.sysintegration.portal.PortalManager;
import com.dsc.nana.services.sysintegration.portal.PortalManagerHome;
import com.dsc.nana.services.sysintegration.tiptop.TiptopManager;
import com.dsc.nana.services.sysintegration.tiptop.TiptopManagerHome;
import com.dsc.nana.util.EJBHomeFactory;
import com.dsc.nana.util.SystemException;
import com.dsc.nana.util.logging.NaNaLog;
import com.dsc.nana.util.logging.NaNaLogFactory;





public class ServiceLocator {
	public static ServiceLocator instance;
	private Context context;

	private ProcessDispatcher processDispatcher;
	private FormDTOFactory formDTOFactory;
	private FormDefinitionManager formDefinitionManager;
	private OrganizationManager organizationManager;
	private ProcessPackageManager processPackageManager;
	private StatefulProcessDispatcher statefulProcessDispatcher;
	private ProcessAbortController processAbortController;
	private PerformWorkItemHandler performWorkItemHandler;
	private ProcessInstanceDTOFactory processInstanceDTOFactory;
	private ImportOrganization importOrganization;
	private FormCategoryManager formCategoryManager;
	private TiptopManager tiptopManager;
	private ListReaderFacade listReaderFacade;
	private PageListReaderFacade pageListReaderFacade;
	private SecurityHandler securityHandler;
	private PortalManager portalManager;
	private SystemConfigManager systemCfgManager;
	private FO_121DLocal FO_121DLocal;
	private DocManagerFactory docManagerFactory;
	private StatefulProcessDispatcherLocal statefulProcessDispatcherLocal;
	
	/** 20100514 vinjaff */
	private DocManager docManager;
	
	/** 20120626 lorenchang */
	private AppFormManager appFormManager;
	/**  SINCE : NANA5.5.3 MODI BY 4182 IN 20130611 */
	private McloudManager mcloudManager;
	
	private NaNaLog log = NaNaLogFactory.getLog(this.getClass());
	

	private ServiceLocator() {

	}

	public static ServiceLocator getInstance() {
		if (instance == null) {
			instance = new ServiceLocator();
		}
		return instance;
	}
	
	/** 20120626 lorenchang */
	public AppFormManager getAppFormManager() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
		AppFormManager tAppFormManager = null;
		AppFormManagerHome tAppFormManagerHome = null;
		if (appFormManager == null) {
			try {
				tAppFormManagerHome = (AppFormManagerHome) tEJBHomeFactory.lookUpHome(AppFormManagerHome.class, "AppFormManager");
				tAppFormManager = tAppFormManagerHome.create();
				appFormManager = tAppFormManager;
			} catch (Exception e) {
				//清理掉.
				appFormManager = null;
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return appFormManager;
	}

	public void setAppFormManager(AppFormManager pAppFormManager) {
		this.appFormManager = pAppFormManager;
	}

	/** 20120626 lorenchang */
	public DocManagerFactory getDocManagerFactory() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
		DocManagerFactory tDocManagerFactory = null;
		DocManagerFactoryHome tDocManagerFactoryHome = null;
		if (docManagerFactory == null) {
			try {
				tDocManagerFactoryHome = (DocManagerFactoryHome) tEJBHomeFactory.lookUpHome(DocManagerFactoryHome.class, "DocManagerFactory");
				tDocManagerFactory = tDocManagerFactoryHome.create();
				docManagerFactory = tDocManagerFactory;
			} catch (Exception e) {
				//清理掉.
				docManagerFactory = null;
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return docManagerFactory;
	}

	public void setDocManagerFactory(DocManagerFactory pDocManagerFactory) {
		this.docManagerFactory = pDocManagerFactory;
	}
	
	/** 20100514 vinjaff */
	public DocManager getDocManager() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
		DocManager tDocManager = null;
		DocManagerHome tDocManagerHome = null;
		if (docManager == null) {
			try {
				tDocManagerHome = (DocManagerHome) tEJBHomeFactory.lookUpHome(DocManagerHome.class, "DocManager");
				tDocManager = tDocManagerHome.create();
				docManager = tDocManager;
			} catch (Exception e) {
				//清理掉

				docManager = null;
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return docManager;
	}
	
	public void setDocManager(DocManager pDocManager) {
		docManager = pDocManager;
	}
	public TiptopManager getTiptopManager() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
		TiptopManager tTiptopManager = null;
		TiptopManagerHome tTiptopManagerHome = null;
		if (tiptopManager == null) {
			try {
				tTiptopManagerHome = (TiptopManagerHome) tEJBHomeFactory.lookUpHome(TiptopManagerHome.class, "TiptopManager");
				tTiptopManager = tTiptopManagerHome.create();
				tiptopManager = tTiptopManager;
			} catch (Exception e) {
				tiptopManager = null;
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return tiptopManager;
	}
	public void setTiptopManager(TiptopManager pTiptopManager) {
		tiptopManager = pTiptopManager;
	}
	// public DotJManager getDotJManager() throws RemoteException {
	// EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
	// DotJManager tDotJManager = null;
	// DotJManagerHome tDotJManagerHome = null;
	// if (dotJManager == null) {
	// try {
	// tDotJManagerHome =
	// (DotJManagerHome)tEJBHomeFactory.lookUpHome(DotJManagerHome.class,
	// "DotJManager");
	// tDotJManager = tDotJManagerHome.create();
	// dotJManager = tDotJManager;
	// } catch (Exception e) {
	// this.log.error(e);
	// throw new RemoteException(e.getMessage());
	// }
	// }
	// return dotJManager;
	// }

	public ProcessDispatcher getProcessDispatcher() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
		ProcessDispatcher tProcessDispatcher = null;
		ProcessDispatcherHome tProcessDispatcherHome = null;
		if (processDispatcher == null) {
			try {
				tProcessDispatcherHome = (ProcessDispatcherHome) tEJBHomeFactory.lookUpHome(ProcessDispatcherHome.class, "ProcessDispatcher");
				tProcessDispatcher = tProcessDispatcherHome.create();
				processDispatcher = tProcessDispatcher;
			} catch (Exception e) {
				processDispatcher = null;
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return processDispatcher;
	}
	
	public void setProcessDispatcher(ProcessDispatcher pProcessDispatcher) {
		processDispatcher = pProcessDispatcher;
	}
	public FormDTOFactory getFormDTOFactory() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
		FormDTOFactory tFormDTOFactory = null;
		FormDTOFactoryHome tFormDTOFactoryHome = null;
		if (formDTOFactory == null) {
			try {
				tFormDTOFactoryHome = (FormDTOFactoryHome) tEJBHomeFactory.lookUpHome(FormDTOFactoryHome.class, "FormDTOFactory");
				tFormDTOFactory = tFormDTOFactoryHome.create();
				formDTOFactory = tFormDTOFactory;
			} catch (Exception e) {
				formDTOFactory = null;
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return formDTOFactory;
	}
	
	public void setFormDTOFactory(FormDTOFactory pFormDTOFactory) {
		formDTOFactory = pFormDTOFactory;
	}
	public FormDefinitionManager getFormDefinitionManager() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
		FormDefinitionManager tFormDefinitionManager = null;
		FormDefinitionManagerHome tFormDefinitionManagerHome = null;
		if (formDefinitionManager == null) {
			try {
				tFormDefinitionManagerHome = (FormDefinitionManagerHome) tEJBHomeFactory.lookUpHome(FormDefinitionManagerHome.class,
						"FormDefinitionManager");
				tFormDefinitionManager = tFormDefinitionManagerHome.create();
				formDefinitionManager = tFormDefinitionManager;
			} catch (Exception e) {
				formDefinitionManager = null;
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return formDefinitionManager;
	}
	
	public void setFormDefinitionManager(FormDefinitionManager pFormDefinitionManager) {
		formDefinitionManager = pFormDefinitionManager;
	}
	
	public FormCategoryManager getFormCategoryManager() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
		FormCategoryManager tFormCategoryManager = null;
		FormCategoryManagerHome tFormCategoryManagerHome = null;
		if (formCategoryManager == null) {
			try {
				tFormCategoryManagerHome = (FormCategoryManagerHome) tEJBHomeFactory.lookUpHome(FormCategoryManagerHome.class, "FormCategoryManager");
				tFormCategoryManager = tFormCategoryManagerHome.create();
				formCategoryManager = tFormCategoryManager;
			} catch (Exception e) {
				formCategoryManager = null;
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return formCategoryManager;
	}
	public void setFormCategoryManager(FormCategoryManager pFormCategoryManager) {
		formCategoryManager = pFormCategoryManager;
	}
	public OrganizationManager getOrganizationManager() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
		OrganizationManager tOrganizationManager = null;
		OrganizationManagerHome tOrganizationManagerHome = null;
		if (organizationManager == null) {
			try {
				tOrganizationManagerHome = (OrganizationManagerHome) tEJBHomeFactory.lookUpHome(OrganizationManagerHome.class, "OrganizationManager");
				tOrganizationManager = tOrganizationManagerHome.create();
				organizationManager = tOrganizationManager;
			} catch (Exception e) {
				organizationManager = null;
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return organizationManager;
	}
	public void setOrganizationManager(OrganizationManager pOrganizationManager) {
		organizationManager = pOrganizationManager;
	}
	public ProcessPackageManager getProcessPackageManager() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
		ProcessPackageManager tProcessPackageManager = null;
		ProcessPackageManagerHome tProcessPackageManagerHome = null;
		if (processPackageManager == null) {
			try {
				tProcessPackageManagerHome = (ProcessPackageManagerHome) tEJBHomeFactory.lookUpHome(ProcessPackageManagerHome.class,
						"ProcessPackageManager");
				tProcessPackageManager = tProcessPackageManagerHome.create();
				processPackageManager = tProcessPackageManager;
			} catch (Exception e) {
				processPackageManager = null;
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return processPackageManager;
	}
	public void setProcessPackageManager(ProcessPackageManager pProcessPackageManager) {
		processPackageManager = pProcessPackageManager;
	}
	public StatefulProcessDispatcher getStatefulProcessDispatcher() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
		StatefulProcessDispatcher tStatefulProcessDispatcher = null;
		StatefulProcessDispatcherHome tStatefulProcessDispatcherHome = null;

		try {
			tStatefulProcessDispatcherHome = (StatefulProcessDispatcherHome) tEJBHomeFactory.lookUpHome(StatefulProcessDispatcherHome.class,
					"StatefulProcessDispatcher");
			tStatefulProcessDispatcher = tStatefulProcessDispatcherHome.create();
			this.setStatefulProcessDispatcher(tStatefulProcessDispatcher);
		} catch (Exception e) {
			this.log.error(e);
			throw new RemoteException(e.getMessage());
		}

		return statefulProcessDispatcher;
	}

	public ProcessAbortController getProcessAbortController() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
		ProcessAbortController tProcessAbortController = null;
		ProcessAbortControllerHome tProcessAbortControllerHome = null;
		if (this.processAbortController == null) {
			try {
				tProcessAbortControllerHome = (ProcessAbortControllerHome) tEJBHomeFactory.lookUpHome(ProcessAbortControllerHome.class,
						"ProcessAbortController");
				tProcessAbortController = tProcessAbortControllerHome.create();
				processAbortController = tProcessAbortController;
			} catch (Exception e) {
				processAbortController = null;
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return processAbortController;
	}
	public void setProcessAbortController(ProcessAbortController pProcessAbortController) {
		processAbortController = pProcessAbortController;
	}
	public PerformWorkItemHandler getPerformWorkItemHandler() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();

		PerformWorkItemHandlerHome tPerformWorkItemHandlerHome = null;
		if (this.performWorkItemHandler == null) {
			try {
				tPerformWorkItemHandlerHome = (PerformWorkItemHandlerHome) tEJBHomeFactory.lookUpHome(PerformWorkItemHandlerHome.class,
						"PerformWorkItemHandler");
				this.performWorkItemHandler = tPerformWorkItemHandlerHome.create();
			} catch (Exception e) {
				performWorkItemHandler = null;
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return this.performWorkItemHandler;
	}
	public void setPerformWorkItemHandler(PerformWorkItemHandler pPerformWorkItemHandler) {
		performWorkItemHandler = pPerformWorkItemHandler;
	}
	public ProcessInstanceDTOFactory getProcessInstanceDTOFactory() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
		ProcessInstanceDTOFactory tProcessInstanceDTOFactory = null;
		ProcessInstanceDTOFactoryHome tProcessInstanceDTOFactoryHome = null;
		if (processInstanceDTOFactory == null) {
			try {
				tProcessInstanceDTOFactoryHome = (ProcessInstanceDTOFactoryHome) tEJBHomeFactory.lookUpHome(ProcessInstanceDTOFactoryHome.class,
						"ProcessInstanceDTOFactory");
				tProcessInstanceDTOFactory = tProcessInstanceDTOFactoryHome.create();
				processInstanceDTOFactory = tProcessInstanceDTOFactory;
			} catch (Exception e) {
				processInstanceDTOFactory = null;
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return processInstanceDTOFactory;
	}
	public void setProcessInstanceDTOFactory(ProcessInstanceDTOFactory pProcessInstanceDTOFactory) {
		processInstanceDTOFactory = pProcessInstanceDTOFactory;
	}
	public void setStatefulProcessDispatcher(StatefulProcessDispatcher pNewValue) {
		statefulProcessDispatcher = pNewValue;
	}

	public ImportOrganization getImportOrganization() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();

		ImportOrganizationHome tImportOrganizationHome = null;
		if (this.importOrganization == null) {
			try {
				tImportOrganizationHome = (ImportOrganizationHome) tEJBHomeFactory.lookUpHome(ImportOrganizationHome.class, "ImportOrganization");
				this.importOrganization = tImportOrganizationHome.create();
			} catch (Exception e) {
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return this.importOrganization;
	}
	public void setImportOrganization(ImportOrganization pImportOrganization) {
		importOrganization = pImportOrganization;
	}
	public ListReaderFacade getListReaderFacade() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();

		ListReaderFacadeHome tListReaderFacadeHome = null;
		if (this.listReaderFacade == null) {
			try {
				tListReaderFacadeHome = (ListReaderFacadeHome) tEJBHomeFactory.lookUpHome(ListReaderFacadeHome.class, "ListReaderFacade");
				this.listReaderFacade = tListReaderFacadeHome.create();
			} catch (Exception e) {
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return this.listReaderFacade;
	}
	public void setListReaderFacade(ListReaderFacade pListReaderFacade) {
		listReaderFacade = pListReaderFacade;
	}
	public PageListReaderFacade getPageListReaderFacade() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();

		PageListReaderFacadeHome tPageListReaderFacadeHome = null;
		if (this.pageListReaderFacade == null) {
			try {
				tPageListReaderFacadeHome = (PageListReaderFacadeHome) tEJBHomeFactory.lookUpHome(PageListReaderFacadeHome.class,
						"PageListReaderFacade");
				this.pageListReaderFacade = tPageListReaderFacadeHome.create();
			} catch (Exception e) {
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return this.pageListReaderFacade;
	}
	public void setPageListReaderFacade(PageListReaderFacade pPageListReaderFacade) {
		pageListReaderFacade = pPageListReaderFacade;
	}
	public SecurityHandler getSecurityHandler() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();

		SecurityHandlerHome tSecurityHandlerHome = null;
		if (this.securityHandler == null) {
			try {
				tSecurityHandlerHome = (SecurityHandlerHome) tEJBHomeFactory.lookUpHome(SecurityHandlerHome.class, "SecurityHandler");
				this.securityHandler = tSecurityHandlerHome.create();
			} catch (Exception e) {
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return this.securityHandler;
	}
	public void setSecurityHandler(SecurityHandler pSecurityHandler) {
		securityHandler = pSecurityHandler;
	}
	public PortalManager getPortalManager() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
		PortalManager tPortalManager = null;
		PortalManagerHome tPortalManagerHome = null;
		if (portalManager == null) {
			try {
				tPortalManagerHome = (PortalManagerHome) tEJBHomeFactory.lookUpHome(PortalManagerHome.class, "PortalManager");
				tPortalManager = tPortalManagerHome.create();
				portalManager = tPortalManager;
			} catch (Exception e) {
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return portalManager;
	}
	public void setPortalManager(PortalManager pPortalManager) {
		portalManager = pPortalManager;
	}
	
	public SystemConfigManager getSystemCfgManager() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();

		SystemConfigManagerHome tSystemConfigManagerHome = null;
		if (this.securityHandler == null) {
			try {
				tSystemConfigManagerHome = (SystemConfigManagerHome) tEJBHomeFactory.lookUpHome(SystemConfigManagerHome.class, "SystemConfigManager");
				this.systemCfgManager = tSystemConfigManagerHome.create();
			} catch (Exception e) {
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return this.systemCfgManager;
	}
	public void setSystemCfgManager(SystemConfigManager pSystemCfgManager) {
		systemCfgManager = pSystemCfgManager;
	}
	/**
	 *  SINCE : NANA5.5.3 MODI BY 4182 IN 20130611 
	 * @return
	 * @throws RemoteException
	 */
	public McloudManager getMcloudManager() throws RemoteException {
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
		McloudManager tMcloudManager = null;
		McloudManagerHome tMcloudManagerHome = null;
		if (mcloudManager == null) {
			try {
				tMcloudManagerHome = (McloudManagerHome) tEJBHomeFactory.lookUpHome(McloudManagerHome.class, "McloudManager");
				tMcloudManager = tMcloudManagerHome.create();
				mcloudManager = tMcloudManager;				
			} catch (Exception e) {
				//清理掉.
				mcloudManager = null;
				this.log.error(e);
				throw new RemoteException(e.getMessage());
			}
		}
		return mcloudManager;
	}

	public void setMcloudManager(McloudManager pMcloudManager) {
		this.mcloudManager = pMcloudManager;
	}
	
	public StatefulProcessDispatcherLocal getStatefulProcessDispatcherLocal() throws ServiceException {
		this.statefulProcessDispatcherLocal = null;
		this.statefulProcessDispatcherLocal = (StatefulProcessDispatcherLocal) this
				.getEJBLocal("StatefulProcessDispatcherLocal");
		return this.statefulProcessDispatcherLocal;
	}

	// add by vinjaff for tiptop integration
	public void setStatefulProcessDispatcherLocal(StatefulProcessDispatcherLocal pNewValue) {
		this.statefulProcessDispatcherLocal = pNewValue;
	}
	
	
	private Object getEJBLocal(String pJndiName) {
		Class[] tMethodParams = new Class[0];
		Object tEJBLocal = null, tEJBLocalHome = null;
		EJBHomeFactory tEJBHomeFactory = EJBHomeFactory.getInstance();
		Map tEJBHomes = tEJBHomeFactory.getEjbHomes();
		if (tEJBHomes != null) {
			if (tEJBHomes.containsKey(pJndiName)) {
				tEJBLocalHome = tEJBHomes.get(pJndiName);
			} else {
				try {
					tEJBLocalHome = EJBHomeFactory.getInstance().lookUpLocalHome(pJndiName);
				} catch (SystemException ex) {
					String tErrMsg = "Fail to lookup ejb " + pJndiName;
					log.error(tErrMsg, ex);
					throw new ServiceException(tErrMsg, ex);
				}
			}
			try {
				Method tMethod = tEJBLocalHome.getClass().getMethod("create", tMethodParams);
				tEJBLocal = tMethod.invoke(tEJBLocalHome, null);
			} catch (Exception ex) {
				String tErrMsg = "An error occurs during creating ejb " + pJndiName;
				log.error(tErrMsg, ex);
				throw new ServiceException(tErrMsg);
			}
		}
		return tEJBLocal;
	}

}
